package br.com.zup.cartaoproposta;

import br.com.zup.cartaoproposta.clienteswebservices.AnaliseCartoesClient;
import br.com.zup.cartaoproposta.controllers.PropostaController;
import br.com.zup.cartaoproposta.entities.analisesolicitante.AnaliseSolicitante;
import br.com.zup.cartaoproposta.entities.analisesolicitante.AnaliseSolicitanteRetorno;
import br.com.zup.cartaoproposta.entities.analisesolicitante.ResultadoSolicitacao;
import br.com.zup.cartaoproposta.entities.proposta.Proposta;
import br.com.zup.cartaoproposta.entities.proposta.PropostaNovoRequest;
import br.com.zup.cartaoproposta.entities.proposta.StatusProposta;
import br.com.zup.cartaoproposta.repositories.PropostaRepository;
import br.com.zup.cartaoproposta.services.analisesolicitante.FeignTratamentoRetorno;
import br.com.zup.cartaoproposta.services.analisesolicitante.RestTemplateTratamentoRetorno;
import br.com.zup.cartaoproposta.services.analisesolicitante.TratamentoRetorno;
import br.com.zup.cartaoproposta.util.ChaveSalt;
import br.com.zup.cartaoproposta.validations.cpfcnpj.CpfCnpjValidador;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PropostaTest {

    private final String documentoApenasNumeros = "50756719000125";
    private final String documentoValido = "50.756.719/0001-25";
    private final String emailValido = "teste@email.com";
    private final String nomeValido = "Nome";
    private final String enderecoValido = "Endereço";
    private final BigDecimal salarioValido = new BigDecimal("1500.00");

    @Autowired
    private ChaveSalt chave;

    @Test
    @DisplayName("deve criar uma entidade proposta")
    void propostaNovoRequestToModel() {

        PropostaNovoRequest novaProposta = new PropostaNovoRequest(documentoValido,emailValido,nomeValido,enderecoValido,salarioValido);
        Proposta proposta = novaProposta.toModel(chave.getChave());

        assertEquals(documentoApenasNumeros,proposta.getDocumento(chave.getChave()));
        assertEquals(emailValido,proposta.getEmail());
        assertEquals(nomeValido,proposta.getNome());
        assertEquals(enderecoValido,proposta.getEndereco());
        assertEquals(salarioValido,proposta.getSalario());
    }

    @Test
    @DisplayName("não aceitar documento invalido")
    void documentoInvalido() {
        CpfCnpjValidador validador = new CpfCnpjValidador();

        boolean valido = validador.isValid("00000000000", null);
        assertFalse(valido);

        valido = validador.isValid("50.756.719/0001-26", null);
        assertFalse(valido);

        valido = validador.isValid("", null);
        assertFalse(valido);
    }

    @Test
    @DisplayName("aceitar cpf")
    void documentoValidoCpf() {
        CpfCnpjValidador validador = new CpfCnpjValidador();

        boolean valido = validador.isValid("064.388.700-80", null);
        assertTrue(valido);

        valido = validador.isValid("69714597099", null);
        assertTrue(valido);
    }

    @Test
    @DisplayName("aceitar cnpj")
    void documentoValidoCnpj() {
        CpfCnpjValidador validador = new CpfCnpjValidador();

        boolean valido = validador.isValid("35.599.966/0001-71", null);
        assertTrue(valido);

        valido = validador.isValid("95172016000198", null);
        assertTrue(valido);
    }

    @Test
    @DisplayName("impedir de salvar proposta de mesmo solicitante")
    void documentoJaCadastrado() {

        PropostaRepository propostaRepository = Mockito.mock(PropostaRepository.class);
        MeterRegistry meterRegistry = Mockito.mock(MeterRegistry.class);

        PropostaController classeDeFluxo = new PropostaController(propostaRepository, meterRegistry);

        PropostaNovoRequest novaProposta = new PropostaNovoRequest(documentoValido,emailValido,nomeValido,enderecoValido,salarioValido);

        UriComponentsBuilder builder = UriComponentsBuilder.newInstance();

        Proposta propostaResultado = novaProposta.toModel(chave.getChave());

        //imitacao
        Mockito.when(propostaRepository.findByDocumento(documentoApenasNumeros)).thenReturn(Optional.of(propostaResultado));

        Assertions.assertThrows(ResponseStatusException.class, () -> classeDeFluxo.cadastroProposta(novaProposta, builder));
    }

    @Test
    @DisplayName("status elegivel - usando Feign")
    void statusElegivelFeign(){

        PropostaNovoRequest novaProposta = new PropostaNovoRequest(documentoValido,emailValido,nomeValido,enderecoValido,salarioValido);
        Proposta proposta = novaProposta.toModel(chave.getChave());

        String idPropostaFake = "id-da-proposta-fake";
        AnaliseSolicitante analiseSolicitante = new AnaliseSolicitante(proposta.getDocumento(chave.getChave()), proposta.getNome(), idPropostaFake);
        AnaliseCartoesClient analiseCartoesClient = Mockito.mock(AnaliseCartoesClient.class);
        //imitacao
        Mockito.when(analiseCartoesClient
                .solicitacaoAnaliseResource(analiseSolicitante))
                .thenReturn(new AnaliseSolicitanteRetorno(proposta.getDocumento(chave.getChave()), proposta.getNome(), ResultadoSolicitacao.SEM_RESTRICAO, idPropostaFake));

        TratamentoRetorno feignTratamentoRetorno = new FeignTratamentoRetorno();
        ReflectionTestUtils.setField(feignTratamentoRetorno,"analiseCartoesClient",analiseCartoesClient);

        AnaliseSolicitanteRetorno retorno = feignTratamentoRetorno.analiseSolicitante(proposta.getDocumento(chave.getChave()), proposta.getNome(), idPropostaFake);

        proposta.defineStatusProposta(retorno.getResultadoSolicitacao());

        assertEquals(proposta.getStatusProposta(), StatusProposta.AGUARDANDO_CARTAO);
    }

    @Test
    @DisplayName("status elegivel - usando RestTemplate")
    void statusElegivelRestTemplate(){

        PropostaNovoRequest novaProposta = new PropostaNovoRequest(documentoValido,emailValido,nomeValido,enderecoValido,salarioValido);
        Proposta proposta = novaProposta.toModel(chave.getChave());

        TratamentoRetorno restTemplateTratamentoRetorno = new RestTemplateTratamentoRetorno();
        ReflectionTestUtils.setField(restTemplateTratamentoRetorno,"url","http://localhost:9999/api");

        AnaliseSolicitanteRetorno retorno = restTemplateTratamentoRetorno.analiseSolicitante(proposta.getDocumento(chave.getChave()), proposta.getNome(), "id-da-proposta-fake");

        proposta.defineStatusProposta(retorno.getResultadoSolicitacao());

        assertEquals(proposta.getStatusProposta(), StatusProposta.AGUARDANDO_CARTAO);
    }


    @Test
    @DisplayName("status não elegivel - usando RestTemplate")
    void statusNaoElegivelRestTemplate(){
        String documentoComRestricao = "38.816.231/0001-40";
        PropostaNovoRequest novaProposta = new PropostaNovoRequest(documentoComRestricao,emailValido,nomeValido,enderecoValido,salarioValido);
        Proposta proposta = novaProposta.toModel(chave.getChave());

        TratamentoRetorno restTemplateTratamentoRetorno = new RestTemplateTratamentoRetorno();
        ReflectionTestUtils.setField(restTemplateTratamentoRetorno,"url","http://localhost:9999/api");

        AnaliseSolicitanteRetorno retorno = restTemplateTratamentoRetorno.analiseSolicitante(proposta.getDocumento(chave.getChave()), proposta.getNome(), "id-da-proposta-fake");

        proposta.defineStatusProposta(retorno.getResultadoSolicitacao());

        assertEquals(proposta.getStatusProposta(), StatusProposta.NAO_ELEGIVEL);
    }

}
