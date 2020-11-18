package br.com.cartao.proposta.controller;

import br.com.cartao.proposta.domain.model.Cartao;
import br.com.cartao.proposta.domain.model.Proposta;
import br.com.cartao.proposta.domain.request.BloqueioRequest;
import br.com.cartao.proposta.domain.request.InformacaoRede;
import br.com.cartao.proposta.service.BloqueioCartaoService;
import br.com.cartao.proposta.service.ColetaInformacaoRedeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
class BloqueioCartaoControllerTest {

    private static String idCartao = "1";
    private static EntityManager manager;
    private static BloqueioCartaoService bloqueioCartaoService;
    private static BloqueioCartaoController bloqueioCartaoController;
    private static HttpServletRequest httpServletRequest;
    private static ColetaInformacaoRedeService informacaoRedeService;
    private static InformacaoRede informacaoRede;

    @BeforeEach
    void setup(){
        manager = mock(EntityManager.class);
        bloqueioCartaoService = mock(BloqueioCartaoService.class);
        informacaoRedeService = mock(ColetaInformacaoRedeService.class);
        httpServletRequest = mock(HttpServletRequest.class);
        informacaoRede = new InformacaoRede("Monzilla", "Localhost");
        bloqueioCartaoController = new BloqueioCartaoController(manager,bloqueioCartaoService,informacaoRedeService);
    }

    @Test
    @DisplayName("Deve bloquear cartao com sucesso e retornar status 201")
    void deveCriarBloquioCartaoComSucessoEStatus_201() throws JsonProcessingException {
        Proposta proposta = new Proposta("documento","email","endereço","nome",new BigDecimal(1000));
        Cartao cartao = new Cartao("1",proposta);
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        BloqueioRequest bloqueioRequest = new BloqueioRequest("localhost", "Postman");

        when(informacaoRedeService.getInformacaoRede(httpServletRequest)).thenReturn(informacaoRede);
        when(manager.find(Cartao.class,"1")).thenReturn(cartao);

        ResponseEntity<?> responseEntity = bloqueioCartaoController.bloqueioCartao(idCartao, bloqueioRequest, uriComponentsBuilder, httpServletRequest);

        Assertions.assertEquals(HttpStatus.CREATED.value(), responseEntity.getStatusCodeValue());

    }

    @Test
    @DisplayName("Não deve achar cartao e retornar 404 ")
    void naoDeveCriarBloquioCartaoComSucessoEStatus_404() throws JsonProcessingException {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        BloqueioRequest bloqueioRequest = new BloqueioRequest("localhost", "Postman");

        when(manager.find(Cartao.class,"1")).thenReturn(null);

        ResponseEntity<?> responseEntity = bloqueioCartaoController.bloqueioCartao(idCartao, bloqueioRequest, uriComponentsBuilder, httpServletRequest);

        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), responseEntity.getStatusCodeValue());

    }
}