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
import br.com.zup.cartaoproposta.services.FeignTratamentoRetorno;
import br.com.zup.cartaoproposta.validations.cpfcnpj.CpfCnpjValidador;
import feign.FeignException;
import feign.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
public class PropostaTest {

    private final String documentoApenasNumeros = "50756719000125";
    private final String documentoValido = "50.756.719/0001-25";
    private final String emailValido = "teste@email.com";
    private final String nomeValido = "Nome";
    private final String enderecoValido = "Endereço";
    private final BigDecimal salarioValido = new BigDecimal("1500.00");

    @Test
    @DisplayName("deve criar uma entidade proposta")
    void propostaNovoRequestToModel() {

        PropostaNovoRequest novaProposta = new PropostaNovoRequest(documentoValido,emailValido,nomeValido,enderecoValido,salarioValido);
        Proposta proposta = novaProposta.toModel();

        assertEquals(documentoApenasNumeros,proposta.getDocumento());
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

        PropostaController classeDeFluxo = new PropostaController(propostaRepository);

        PropostaNovoRequest novaProposta = new PropostaNovoRequest(documentoValido,emailValido,nomeValido,enderecoValido,salarioValido);

        UriComponentsBuilder builder = UriComponentsBuilder.newInstance();

        Proposta propostaResultado = novaProposta.toModel();

        //imitacao
        Mockito.when(propostaRepository.findByDocumento(documentoApenasNumeros)).thenReturn(Optional.of(propostaResultado));

        Assertions.assertThrows(ResponseStatusException.class, () -> classeDeFluxo.criaProposta(novaProposta, builder));
    }

    @Test
    @DisplayName("status elegivel")
    void statusElegivel(){

        PropostaNovoRequest novaProposta = new PropostaNovoRequest(documentoValido,emailValido,nomeValido,enderecoValido,salarioValido);
        Proposta proposta = novaProposta.toModel();

        String idPropostaFake = "id-da-proposta-fake";
        AnaliseSolicitante analiseSolicitante = new AnaliseSolicitante(proposta.getDocumento(), proposta.getNome(), idPropostaFake);
        AnaliseCartoesClient analiseCartoesClient = Mockito.mock(AnaliseCartoesClient.class);
        //imitacao
        Mockito.when(analiseCartoesClient
                .solicitacaoAnaliseResource(analiseSolicitante))
                .thenReturn(new AnaliseSolicitanteRetorno(proposta.getDocumento(), proposta.getNome(), ResultadoSolicitacao.SEM_RESTRICAO, idPropostaFake));



        FeignTratamentoRetorno feignTratamentoRetorno = new FeignTratamentoRetorno(analiseCartoesClient);
        AnaliseSolicitanteRetorno retorno = feignTratamentoRetorno.analiseSolicitante(proposta.getDocumento(), proposta.getNome(), "id-da-proposta-fake");

        proposta.defineStatusProposta(retorno.getResultadoSolicitacao());

        assertEquals(proposta.getStatusProposta(), StatusProposta.ELEGIVEL);
    }

    //Teste com erro para gerar a exeção FeignException no mock
//    @Test
    @DisplayName("status não elegivel")
    void statusNaoElegivel(){
        String documentoComRestricao = "38.816.231/0001-40";
        PropostaNovoRequest novaProposta = new PropostaNovoRequest(documentoComRestricao,emailValido,nomeValido,enderecoValido,salarioValido);
        Proposta proposta = novaProposta.toModel();

        String idPropostaFake = "id-da-proposta-fake";
        AnaliseSolicitante analiseSolicitante = new AnaliseSolicitante(proposta.getDocumento(), proposta.getNome(), idPropostaFake);
        AnaliseCartoesClient analiseCartoesClient = Mockito.mock(AnaliseCartoesClient.class);

        //imitacao
        doThrow(FeignException.errorStatus("AnaliseCartoesClient",
                        Response.builder()
                                .headers(Collections.emptyMap())
                                .status(422)
                                .body(String.format(
                                        "Erro[{" +
                                                "\"documento\":\"%s\"," +
                                                "\"nome\":\"%s\"," +
                                                "\"resultadoSolicitacao\":\"COM_RESTRICAO\"," +
                                                "\"idProposta\":\"%s\"" +
                                                "}]",
                                        analiseSolicitante.getDocumento(),
                                        analiseSolicitante.getNome(),
                                        analiseSolicitante.getIdProposta()).getBytes()
                                ).build()
                ))
                .when(analiseCartoesClient.solicitacaoAnaliseResource(analiseSolicitante));

        FeignTratamentoRetorno feignTratamentoRetorno = new FeignTratamentoRetorno(analiseCartoesClient);
        AnaliseSolicitanteRetorno retorno = feignTratamentoRetorno.analiseSolicitante(documentoValido, nomeValido, "id-da-proposta-fake");

        proposta.defineStatusProposta(retorno.getResultadoSolicitacao());

        assertEquals(proposta.getStatusProposta(), StatusProposta.NAO_ELEGIVEL);
    }

}
