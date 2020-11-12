package dev.arielalvesdutra.proposta.test.it.services;


import dev.arielalvesdutra.proposta.entities.AvisoViagem;
import dev.arielalvesdutra.proposta.entities.Cartao;
import dev.arielalvesdutra.proposta.entities.Proposta;
import dev.arielalvesdutra.proposta.http_clients.AnaliseHttpClient;
import dev.arielalvesdutra.proposta.http_clients.CartaoHttpClient;
import dev.arielalvesdutra.proposta.http_clients.dtos.NotificacaoAvisoViagemDTO;
import dev.arielalvesdutra.proposta.http_clients.dtos.ResultadoAvisoViagemDTO;
import dev.arielalvesdutra.proposta.http_clients.dtos.SolicitacaoAnaliseDTO;
import dev.arielalvesdutra.proposta.http_clients.dtos.enums.ResultadoAvisoViagemStatus;
import dev.arielalvesdutra.proposta.repositories.CartaoRepository;
import dev.arielalvesdutra.proposta.services.AvisoViagemService;
import dev.arielalvesdutra.proposta.services.CartaoService;
import dev.arielalvesdutra.proposta.services.PropostaService;
import dev.arielalvesdutra.proposta.services.dtos.CadastrarAvisoViagemDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static dev.arielalvesdutra.proposta.test.factories.controllers.dtos.CadastrarPropostaDTOFactory.propostaValida;
import static dev.arielalvesdutra.proposta.test.factories.entities.CartaoFactory.cartaoValido;
import static dev.arielalvesdutra.proposta.test.factories.http_clients.dtos.ResultadoAnaliseDTOFactory.resultadoAnaliseSemRestricao;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class AvisoViagemServiceIT {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private AvisoViagemService avisoViagemService;

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
    public void cadastrar_deveCadastrarUmAvisoDeViagem() {
        var cartaoCadastrado = cadastraCartaoValido();
        var dto = new CadastrarAvisoViagemDTO()
                .setIp("0.0.0.0")
                .setTermino(LocalDate.now().plusDays(10))
                .setUserAgent("Postman/10.2.1")
                .setDestino("Paris - França");
        when(cartaoHttpClientMock.notificaAvisoDeViagem(any(String.class), any(NotificacaoAvisoViagemDTO.class)))
                .thenReturn(resultadoAvisoViagemSucesso());

        AvisoViagem avisoViagemCadastrado = avisoViagemService.cadastrar(cartaoCadastrado.getId(), dto);

        assertThat(avisoViagemCadastrado).isNotNull();
        assertThat(avisoViagemCadastrado.getId()).isNotNull();

        AvisoViagem avisoBuscado = avisoViagemService.buscaPeloId(avisoViagemCadastrado.getId());

        assertThat(avisoBuscado).isNotNull();
        assertThat(avisoBuscado.getId()).isEqualTo(avisoViagemCadastrado.getId());
        assertThat(avisoBuscado.getCartao()).isEqualTo(cartaoCadastrado);
        assertThat(avisoBuscado.getDestino()).isEqualTo(dto.getDestino());
        assertThat(avisoBuscado.getIp()).isEqualTo(dto.getIp());
        assertThat(avisoBuscado.getTermino()).isEqualTo(dto.getTermino());
        assertThat(avisoBuscado.getUserAgent()).isEqualTo(dto.getUserAgent());
        assertThat(avisoBuscado.getCadatradoEm()).isNotNull();
    }

    private Cartao cadastraCartaoValido() {
        var cartaoParaCadastrar = cartaoValido(proposta);
        return cartaoRepository.save(cartaoParaCadastrar);
    }

    private ResultadoAvisoViagemDTO resultadoAvisoViagemSucesso() {
        return new ResultadoAvisoViagemDTO()
                .setResultado(ResultadoAvisoViagemStatus.CRIADO);
    }
}
