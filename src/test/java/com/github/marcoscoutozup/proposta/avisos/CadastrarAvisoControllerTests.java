package com.github.marcoscoutozup.proposta.avisos;

import com.github.marcoscoutozup.proposta.cartao.CartaoClient;
import com.github.marcoscoutozup.proposta.exception.StandardError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityManager;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CadastrarAvisoControllerTests {

    @Mock
    private EntityManager entityManager;

    @Mock
    private CartaoClient cartaoClient;

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
        ResponseEntity responseEntity = controller.cadastrarBiometria(UUID.randomUUID(), null, null, null);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof StandardError);
    }
}
