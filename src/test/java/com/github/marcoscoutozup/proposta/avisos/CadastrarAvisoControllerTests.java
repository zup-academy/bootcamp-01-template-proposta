package com.github.marcoscoutozup.proposta.avisos;

import com.github.marcoscoutozup.proposta.cartao.Cartao;
import com.github.marcoscoutozup.proposta.cartao.CartaoClient;
import com.github.marcoscoutozup.proposta.exception.StandardError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CadastrarAvisoControllerTests {

    @Mock
    private EntityManager entityManager;

    @Mock
    private CartaoClient cartaoClient;

    @Mock
    private AvisoRequest avisoRequest;

    @Mock
    private Cartao cartao;

    private CadastrarAvisoController controller;

    @BeforeEach
    public void setup(){
        initMocks(this);
        controller = new CadastrarAvisoController(entityManager, cartaoClient);
    }

    @Test
    @DisplayName("Não deve cadastrar aviso se cartão não foi encontrado")
    public void naoDeveCadastrarAvisoSeCartaoNaoFoiEncontrado(){
        when(entityManager.find(any(), any(UUID.class))).thenReturn(null);
        ResponseEntity responseEntity = controller.cadastrarAvisoDeViagem(UUID.randomUUID(), null, null, new String(), UriComponentsBuilder.newInstance());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof StandardError);
    }

    @Test
    @DisplayName("Não deve cadastrar aviso se cartão não pertence ao solicitante")
    public void naoDeveCadastrarAvisoSeCartaoNaoPertenceAoSolicitante(){
        when(entityManager.find(any(), any(UUID.class))).thenReturn(cartao);
        when(cartao.verificarSeOEmailDoTokenEOMesmoDoCartao(anyString())).thenReturn(false);
        ResponseEntity responseEntity = controller.cadastrarAvisoDeViagem(UUID.randomUUID(), null, null, new String(), UriComponentsBuilder.newInstance());
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof StandardError);
    }

    @Test
    @DisplayName("Deve cadastrar aviso de viagem")
    public void deveCadastrarAvisoDeViagem(){
        when(entityManager.find(any(), any(UUID.class))).thenReturn(cartao);
        when(cartao.verificarSeOEmailDoTokenEOMesmoDoCartao(anyString())).thenReturn(true);
        when(avisoRequest.toAviso(any())).thenReturn(new Aviso());
        ResponseEntity responseEntity = controller.cadastrarAvisoDeViagem(UUID.randomUUID(), avisoRequest, null, new String(), UriComponentsBuilder.newInstance());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertTrue(responseEntity.getHeaders().containsKey("Location") );
    }
}
