package dev.arielalvesdutra.proposta.test.it.services;

import dev.arielalvesdutra.proposta.entities.Cartao;
import dev.arielalvesdutra.proposta.entities.Proposta;
import dev.arielalvesdutra.proposta.entities.enums.CartaoStatus;
import dev.arielalvesdutra.proposta.http_clients.AnaliseHttpClient;
import dev.arielalvesdutra.proposta.http_clients.CartaoHttpClient;
import dev.arielalvesdutra.proposta.http_clients.dtos.SolicitacaoAnaliseDTO;
import dev.arielalvesdutra.proposta.http_clients.dtos.SolicitacaoBloqueioDTO;
import dev.arielalvesdutra.proposta.repositories.CartaoBloqueioRepository;
import dev.arielalvesdutra.proposta.repositories.CartaoRepository;
import dev.arielalvesdutra.proposta.services.CartaoService;
import dev.arielalvesdutra.proposta.services.PropostaService;
import dev.arielalvesdutra.proposta.services.dtos.CadastrarBloqueioDTO;
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
import static dev.arielalvesdutra.proposta.test.factories.http_clients.dtos.CartaoRetornoDTOFactory.cartaoCadastrado;
import static dev.arielalvesdutra.proposta.test.factories.http_clients.dtos.ResultadoAnaliseDTOFactory.resultadoAnaliseSemRestricao;
import static dev.arielalvesdutra.proposta.test.factories.http_clients.dtos.ResultadoBloqueioDTOFactory.resultadoBlequeioSucesso;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class CartaoServiceIT {

    @MockBean
    private CartaoHttpClient cartaoHttpClientMock;

    @MockBean
    private AnaliseHttpClient analiseHttpClientMock;

    @Autowired
    private PropostaService propostaService;

    @Autowired
    private CartaoService cartaoService;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private CartaoBloqueioRepository cartaoBloqueioRepository;

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
    public void sincronizaNovosCartoesEmitidosNoServicoDeCartoes_deveFucionar() {
        var cartaoRetornoDTO = cartaoCadastrado(proposta);
        when(cartaoHttpClientMock.buscaCartao(proposta.getId())).thenReturn(cartaoRetornoDTO);

        cartaoService.sincronizaNovosCartoesEmitidosNoServicoDeCartoes();

        var cartaoBuscado = cartaoService.buscaPelaPropostaId(proposta.getId());

        assertThat(cartaoBuscado).isNotNull();
        assertThat(cartaoBuscado.getId()).isNotNull();
        assertThat(cartaoBuscado.getLegadoId()).isEqualTo(cartaoRetornoDTO.getId());
        assertThat(cartaoBuscado.getLimite()).isEqualTo(cartaoRetornoDTO.getLimite());
        assertThat(cartaoBuscado.getTitular()).isEqualTo(cartaoRetornoDTO.getTitular());

        var propostaBuscada  = propostaService.buscaPeloId(proposta.getId());

        assertThat(propostaBuscada).isNotNull();
        assertThat(propostaBuscada.getCartao()).isEqualTo(cartaoBuscado);
    }

    @Test
    public void bloquear_deveBloquearCartaoERetornarNovoRegistroDeBloqueio() {
        var cartaoCadastrado = cadastraCartaoValido();

        assertThat(cartaoCadastrado.getStatus()).isEqualTo(CartaoStatus.ATIVO);

        var dto = new CadastrarBloqueioDTO()
                .setIp("0.0.0.0")
                .setUserAgent("Postman/3030");
        when(cartaoHttpClientMock.bloqueiaCartao(any(String.class), any(SolicitacaoBloqueioDTO.class)))
                .thenReturn(resultadoBlequeioSucesso());

        var bloqueioCadastrado = cartaoService.bloquear(cartaoCadastrado.getId(), dto);

        assertThat(bloqueioCadastrado).isNotNull();
        assertThat(bloqueioCadastrado.getId()).isNotNull();

        var bloqueioBuscado = cartaoBloqueioRepository.findById(bloqueioCadastrado.getId()).get();

        assertThat(bloqueioBuscado).isNotNull();
        assertThat(bloqueioBuscado.getId()).isNotNull();
        assertThat(bloqueioBuscado.getIp()).isEqualTo(dto.getIp());
        assertThat(bloqueioBuscado.getUserAgent()).isEqualTo(dto.getUserAgent());
        assertThat(bloqueioBuscado.getCartao()).isEqualTo(cartaoCadastrado);
        assertThat(bloqueioBuscado.getCadastradoEm()).isNotNull();

        var cartaoBuscado = cartaoService.buscaPeloId(cartaoCadastrado.getId());

        assertThat(cartaoBuscado).isNotNull();
        assertThat(cartaoBuscado.getStatus()).isEqualTo(CartaoStatus.BLOQUEADO);
    }

    private Cartao cadastraCartaoValido() {
        var cartaoParaCadastrar = cartaoValido(proposta);
        return cartaoRepository.save(cartaoParaCadastrar);
    }
}
