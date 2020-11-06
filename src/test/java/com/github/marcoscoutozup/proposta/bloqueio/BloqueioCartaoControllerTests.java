package com.github.marcoscoutozup.proposta.bloqueio;

import com.github.marcoscoutozup.proposta.cartao.Cartao;
import com.github.marcoscoutozup.proposta.exception.StandardError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class BloqueioCartaoControllerTests {

    @Mock
    private EntityManager entityManager;

    @Mock
    private BloqueioService bloqueioService;

    @Mock
    private Cartao cartao;

    @Mock
    private HttpServletRequest request;

    private BloquearCartaoController controller;

    @BeforeEach
    public void setup(){
        initMocks(this);
        controller = new BloquearCartaoController(entityManager, bloqueioService);
    }

    @Test
    @DisplayName("Não deve bloquear se cartão não foi encontrado")
    public void naoDeveBloquearSeCartaoNaoFoiEncontrado(){
        when(entityManager.find(any(), any(UUID.class))).thenReturn(null);
        ResponseEntity responseEntity = controller.bloquearCartao(UUID.randomUUID(), null, new String(), UriComponentsBuilder.newInstance());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof StandardError);
    }

    @Test
    @DisplayName("Não deve bloquear se cartão não pertence ao solicitante")
    public void naoDeveBloquearSeCartaoNaoPertenceAoSolicitante(){
        when(entityManager.find(any(), any(UUID.class))).thenReturn(cartao);
        when(cartao.verificarSeOEmailDoTokenEOMesmoDoCartao(anyString())).thenReturn(false);
        ResponseEntity responseEntity = controller.bloquearCartao(UUID.randomUUID(), null, new String(), UriComponentsBuilder.newInstance());
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof StandardError);
    }

    @Test
    @DisplayName("Deve bloquear cartão")
    public void deveBloquearCartao(){
        when(entityManager.find(any(), any(UUID.class))).thenReturn(cartao);
        when(cartao.verificarSeOEmailDoTokenEOMesmoDoCartao(anyString())).thenReturn(true);
        ResponseEntity responseEntity = controller.bloquearCartao(UUID.randomUUID(), request, new String(), UriComponentsBuilder.newInstance());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertTrue(responseEntity.getHeaders().containsKey("Location"));
    }

}
