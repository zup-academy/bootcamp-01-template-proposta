package com.github.marcoscoutozup.proposta.proposta;

import com.github.marcoscoutozup.proposta.exception.StandardError;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ConsultaPropostaControllerTests {

    private ConsultaPropostaController controller;

    @Mock
    private PropostaRepository repository;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        controller = new ConsultaPropostaController(repository);
    }

    @Test
    @DisplayName("Deve retornar a consulta da proposta")
    public void deveRetornarAConsultaDaProposta(){
        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(new Proposta()));
        ResponseEntity responseEntity = controller.consultarPropostaPorId(UUID.randomUUID());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertTrue(responseEntity.getBody() instanceof PropostaResponse);
    }

    @Test
    @DisplayName("Não deve retornar a consulta da proposta")
    public void naoDeveRetornarAConsultaDaProposta(){
        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());
        ResponseEntity responseEntity = controller.consultarPropostaPorId(UUID.randomUUID());
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assert.assertTrue(responseEntity.getBody() instanceof StandardError);
    }


}
