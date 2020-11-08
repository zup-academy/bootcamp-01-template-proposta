package dev.arielalvesdutra.proposta.test.it.services;


import dev.arielalvesdutra.proposta.entities.Cartao;
import dev.arielalvesdutra.proposta.entities.Proposta;
import dev.arielalvesdutra.proposta.entities.SolicitacaoRecuperacaoSenha;
import dev.arielalvesdutra.proposta.http_clients.AnaliseHttpClient;
import dev.arielalvesdutra.proposta.http_clients.CartaoHttpClient;
import dev.arielalvesdutra.proposta.http_clients.dtos.SolicitacaoAnaliseDTO;
import dev.arielalvesdutra.proposta.repositories.CartaoRepository;
import dev.arielalvesdutra.proposta.services.CartaoService;
import dev.arielalvesdutra.proposta.services.PropostaService;
import dev.arielalvesdutra.proposta.services.SolicitacaoRecuperacaoSenhaService;
import dev.arielalvesdutra.proposta.services.dtos.CadastrarSolicitacaoRecuperacaoSenhaDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static dev.arielalvesdutra.proposta.test.factories.controllers.dtos.CadastrarPropostaDTOFactory.propostaValida;
import static dev.arielalvesdutra.proposta.test.factories.entities.CartaoFactory.cartaoValido;
import static dev.arielalvesdutra.proposta.test.factories.http_clients.dtos.ResultadoAnaliseDTOFactory.resultadoAnaliseSemRestricao;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class SolicitacaoRecuperacaoSenhaServiceIT {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private SolicitacaoRecuperacaoSenhaService solicitacaoRecuperacaoSenhaService;

    @MockBean
    private CartaoHttpClient cartaoHttpClientMock;

    @MockBean
    private AnaliseHttpClient analiseHttpClientMock;

    @Autowired
    private PropostaService propostaService;

    @Autowired
    private CartaoService cartaoService;

    private Proposta proposta;

    @BeforeEach
    public void setUp() {
        when(analiseHttpClientMock.analisaDadosFinanceiros(any(SolicitacaoAnaliseDTO.class)))
                .thenReturn(resultadoAnaliseSemRestricao());
        proposta = propostaService.cadastrar(propostaValida());
    }

    @AfterEach
    public void tearDown() {
        cartaoService.removeTodos();
        propostaService.removeTodos();
    }

    @Test
    public void cadastrar_deveCadastrarUmaSolicitacaoDeRecuperacaoDeSenha() {
        var cartaoCadastrado = cadastraCartaoValido();
        var dto = new CadastrarSolicitacaoRecuperacaoSenhaDTO()
                .setIp("0.0.0.0")
                .setUserAgent("PostmanRuntime/7.26.8");

        SolicitacaoRecuperacaoSenha solicitacaoCadastrada = solicitacaoRecuperacaoSenhaService.cadastrar(cartaoCadastrado.getId(), dto);

        assertThat(solicitacaoCadastrada).isNotNull();
        assertThat(solicitacaoCadastrada.getId()).isNotNull();

        var solicitacaoBuscada = solicitacaoRecuperacaoSenhaService.buscaPeloId(solicitacaoCadastrada.getId());

        assertThat(solicitacaoBuscada).isNotNull();
        assertThat(solicitacaoBuscada.getId()).isEqualTo(solicitacaoCadastrada.getId());
        assertThat(solicitacaoBuscada.getIp()).isEqualTo(dto.getIp());
        assertThat(solicitacaoBuscada.getUserAgent()).isEqualTo(dto.getUserAgent());
        assertThat(solicitacaoBuscada.getCadastradoEm()).isNotNull();
        assertThat(solicitacaoBuscada.getCartao()).isEqualTo(cartaoCadastrado);
    }

    private Cartao cadastraCartaoValido() {
        var cartaoParaCadastrar = cartaoValido(proposta);
        return cartaoRepository.save(cartaoParaCadastrar);
    }
}
