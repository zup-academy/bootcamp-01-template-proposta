package com.github.marcoscoutozup.proposta.proposta;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.marcoscoutozup.proposta.analisefinanceira.AnaliseFinanceiraService;
import com.github.marcoscoutozup.proposta.exception.StandardError;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CadastrarPropostaControllerTests {

    @Mock
    private PropostaRepository propostaRepository;

    @Mock
    private AnaliseFinanceiraService analiseFinanceiraService;

    @Mock
    private Tracer tracer;

    @Mock
    private Span span;

    private UriComponentsBuilder builder;

    private CadastrarPropostaController cadastrarPropostaController;

    @BeforeEach
    public void setup(){
        initMocks(this);
        builder = UriComponentsBuilder.newInstance();
    }

    @Test
    @DisplayName("Não deve cadastrar proposta - Status code 422")
    public void naoDeveCadastrarProposta() throws JsonProcessingException {
        cadastrarPropostaController = new CadastrarPropostaController(propostaRepository, null, tracer);
        when(tracer.activeSpan()).thenReturn(span);
        when(propostaRepository.findByDocumento(any(String.class))).thenReturn(Optional.of(new Proposta()));
        ResponseEntity responseEntity = cadastrarPropostaController.cadastrarProposta(propostaDtoMock(), builder);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof StandardError);
    }

    @Test
    @DisplayName("Deve cadastrar proposta - Status code 201")
    public void deveCadastrarProposta() throws JsonProcessingException {
        cadastrarPropostaController = new CadastrarPropostaController(propostaRepository, analiseFinanceiraService, tracer);
        when(tracer.activeSpan()).thenReturn(span);
        when(propostaRepository.findByDocumento(any(String.class))).thenReturn(Optional.empty());
        ResponseEntity responseEntity = cadastrarPropostaController.cadastrarProposta(propostaDtoMock(), builder);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertTrue(responseEntity.getHeaders().containsKey("Location"));
    }
            //5
    public PropostaRequest propostaDtoMock(){
        return new PropostaRequest("49258122038", "pessoa@email.com", "Pesssoa 1", "Rua um, 123", new BigDecimal(2000));
    }

}
