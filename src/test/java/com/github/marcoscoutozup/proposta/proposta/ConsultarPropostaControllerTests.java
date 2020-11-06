package com.github.marcoscoutozup.proposta.proposta;

import com.github.marcoscoutozup.proposta.exception.StandardError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ConsultarPropostaControllerTests {

    private ConsultarPropostaController controller;

    @Mock
    private PropostaRepository repository;

    @Mock
    private Proposta proposta;

    @BeforeEach
    public void setup(){
        initMocks(this);
        controller = new ConsultarPropostaController(repository);
    }

    @Test
    @DisplayName("Deve retornar a consulta da proposta")
    public void deveRetornarAConsultaDaProposta(){
        when(repository.findById(any(UUID.class))).thenReturn(Optional.of(proposta));
        when(proposta.descriptografarDocumento()).thenReturn(new String());
        ResponseEntity responseEntity = controller.consultarPropostaPorId(UUID.randomUUID());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof PropostaResponse);
    }

    @Test
    @DisplayName("Não deve retornar a consulta da proposta")
    public void naoDeveRetornarAConsultaDaProposta(){
        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());
        ResponseEntity responseEntity = controller.consultarPropostaPorId(UUID.randomUUID());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof StandardError);
    }


}
