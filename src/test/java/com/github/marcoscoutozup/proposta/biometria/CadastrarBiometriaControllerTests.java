package com.github.marcoscoutozup.proposta.biometria;

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

public class CadastrarBiometriaControllerTests {

    @Mock
    private EntityManager entityManager;
    private CadastrarBiometriaController controller;

    @BeforeEach
    public void setup(){
        initMocks(this);
        controller = new CadastrarBiometriaController(entityManager);
    }

    @Test
    @DisplayName("Não deve cadastrar biometria com número de cartão inválido")
    public void naoDeveCadastrarBiometriaComCartaoInvalido(){
        when(entityManager.find(any(), any(UUID.class))).thenReturn(null);
        ResponseEntity responseEntity = controller.cadastrarBiometria(UUID.randomUUID(), new BiometriaDTO(), null);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof StandardError);
    }

}
