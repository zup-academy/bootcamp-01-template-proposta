package com.github.marcoscoutozup.proposta.biometria;

import com.github.marcoscoutozup.proposta.exception.StandardError;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityManager;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CadastrarBiometriaControllerTests {

    @Mock
    private EntityManager entityManager;
    private CadastrarBiometriaController controller;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        controller = new CadastrarBiometriaController(entityManager);
    }

    @Test
    @DisplayName("Não deve cadastrar biometria com número de cartão inválido")
    public void naoDeveCadastrarBiometriaComCartaoInvalido(){
        when(entityManager.find(any(), any(UUID.class))).thenReturn(null);
        ResponseEntity responseEntity = controller.cadastrarBiometria(UUID.randomUUID(), new BiometriaDTO(), null);
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assert.assertTrue(responseEntity.getBody() instanceof StandardError);
    }

}
