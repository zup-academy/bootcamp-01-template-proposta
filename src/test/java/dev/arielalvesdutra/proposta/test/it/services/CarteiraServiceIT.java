package dev.arielalvesdutra.proposta.test.it.services;

import dev.arielalvesdutra.proposta.entities.Cartao;
import dev.arielalvesdutra.proposta.entities.Carteira;
import dev.arielalvesdutra.proposta.entities.Proposta;
import dev.arielalvesdutra.proposta.entities.enums.CarteiraTipo;
import dev.arielalvesdutra.proposta.http_clients.AnaliseHttpClient;
import dev.arielalvesdutra.proposta.http_clients.CartaoHttpClient;
import dev.arielalvesdutra.proposta.http_clients.dtos.ResultadoAssociacaoCarteiraDTO;
import dev.arielalvesdutra.proposta.http_clients.dtos.SolicitacaoAnaliseDTO;
import dev.arielalvesdutra.proposta.http_clients.dtos.SolicitacaoAssociacaoCarteiraDTO;
import dev.arielalvesdutra.proposta.http_clients.dtos.enums.ResultadoAssociacaoCarteiraStatus;
import dev.arielalvesdutra.proposta.repositories.CartaoRepository;
import dev.arielalvesdutra.proposta.services.CartaoService;
import dev.arielalvesdutra.proposta.services.CarteiraService;
import dev.arielalvesdutra.proposta.services.PropostaService;
import dev.arielalvesdutra.proposta.services.dtos.AssociarCarteiraDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static dev.arielalvesdutra.proposta.test.factories.controllers.dtos.CadastrarPropostaDTOFactory.propostaValida;
import static dev.arielalvesdutra.proposta.test.factories.entities.CartaoFactory.cartaoValido;
import static dev.arielalvesdutra.proposta.test.factories.http_clients.dtos.ResultadoAnaliseDTOFactory.resultadoAnaliseSemRestricao;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class CarteiraServiceIT {

    @Autowired
    private CarteiraService carteiraService;

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
    public void associa_comPaypal_deveCadastrarCarteiraDigital() {
        var resultadoSucessoDTO = resultadoAssociacaoCarteiraSucesso();
        when(cartaoHttpClientMock.associaCarteira(any(String.class), any(SolicitacaoAssociacaoCarteiraDTO.class)))
                .thenReturn(resultadoSucessoDTO);
        var cartaoValido = cadastraCartaoValido();
        AssociarCarteiraDTO dto = new AssociarCarteiraDTO()
                .setTipo(CarteiraTipo.PAYPAL)
                .setEmail(proposta.getEmail());

        Carteira carteiraCadastrada = carteiraService.associa(cartaoValido.getId(), dto);

        assertThat(carteiraCadastrada).isNotNull();
        assertThat(carteiraCadastrada.getId()).isNotNull();

        Carteira carteiraBuscada = carteiraService.buscaPeloId(carteiraCadastrada.getId());

        assertThat(carteiraBuscada).isNotNull();
        assertThat(carteiraBuscada.getId()).isEqualTo(carteiraCadastrada.getId());
        assertThat(carteiraBuscada.getEmail()).isEqualTo(dto.getEmail());
        assertThat(carteiraBuscada.getTipo()).isEqualTo(CarteiraTipo.PAYPAL);
        assertThat(carteiraBuscada.getCartao()).isEqualTo(cartaoValido);
        assertThat(carteiraBuscada.getCadastradoEm()).isNotNull();
    }

    @Test
    public void associa_comPaypal_duplicado_deveLancarException() {
        try {


        var resultadoSucessoDTO = resultadoAssociacaoCarteiraSucesso();
        when(cartaoHttpClientMock.associaCarteira(any(String.class), any(SolicitacaoAssociacaoCarteiraDTO.class)))
                .thenReturn(resultadoSucessoDTO);
        var cartaoValido = cadastraCartaoValido();
        AssociarCarteiraDTO dto = new AssociarCarteiraDTO()
                .setTipo(CarteiraTipo.SAMSUNG_PLAY)
                .setEmail(proposta.getEmail());

        carteiraService.associa(cartaoValido.getId(), dto);
        carteiraService.associa(cartaoValido.getId(), dto);

        } catch (ResponseStatusException e) {
            assertThat(e.getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Test
    public void associa_comSamsungPlay_deveCadastrarCarteiraDigital() {
        var resultadoSucessoDTO = resultadoAssociacaoCarteiraSucesso();
        when(cartaoHttpClientMock.associaCarteira(any(String.class), any(SolicitacaoAssociacaoCarteiraDTO.class)))
                .thenReturn(resultadoSucessoDTO);
        var cartaoValido = cadastraCartaoValido();
        AssociarCarteiraDTO dto = new AssociarCarteiraDTO()
                .setTipo(CarteiraTipo.SAMSUNG_PLAY)
                .setEmail(proposta.getEmail());

        Carteira carteiraCadastrada = carteiraService.associa(cartaoValido.getId(), dto);

        assertThat(carteiraCadastrada).isNotNull();
        assertThat(carteiraCadastrada.getId()).isNotNull();

        Carteira carteiraBuscada = carteiraService.buscaPeloId(carteiraCadastrada.getId());

        assertThat(carteiraBuscada).isNotNull();
        assertThat(carteiraBuscada.getId()).isEqualTo(carteiraCadastrada.getId());
        assertThat(carteiraBuscada.getEmail()).isEqualTo(dto.getEmail());
        assertThat(carteiraBuscada.getTipo()).isEqualTo(CarteiraTipo.SAMSUNG_PLAY);
        assertThat(carteiraBuscada.getCartao()).isEqualTo(cartaoValido);
        assertThat(carteiraBuscada.getCadastradoEm()).isNotNull();
    }

    private Cartao cadastraCartaoValido() {
        var cartaoParaCadastrar = cartaoValido(proposta);
        return cartaoRepository.save(cartaoParaCadastrar);
    }

    private ResultadoAssociacaoCarteiraDTO resultadoAssociacaoCarteiraSucesso() {
        return new ResultadoAssociacaoCarteiraDTO()
                .setId(UUID.randomUUID().toString())
                .setResultado(ResultadoAssociacaoCarteiraStatus.ASSOCIADA);
    }
}
