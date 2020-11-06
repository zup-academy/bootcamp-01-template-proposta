package com.github.marcoscoutozup.proposta.proposta;

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
    private Proposta proposta;

    @Mock
    private PropostaRequest request;

    @Mock
    private Span span;

    private CadastrarPropostaController cadastrarPropostaController;

    @BeforeEach
    public void setup(){
        initMocks(this);
        cadastrarPropostaController = new CadastrarPropostaController(propostaRepository, analiseFinanceiraService, tracer);
    }

    @Test
    @DisplayName("Não deve cadastrar mais de uma proposta com o mesmo documento")
    public void naoDeveCadastrarMaisDeUmaPropostaComOMesmoDocumento() {
        when(tracer.activeSpan()).thenReturn(span);
        when(propostaRepository.findByDocumento(any())).thenReturn(Optional.of(proposta));
        when(request.toProposta()).thenReturn(proposta);
        when(request.getDocumento()).thenReturn(new String());
        ResponseEntity responseEntity = cadastrarPropostaController.cadastrarProposta(request, UriComponentsBuilder.newInstance());
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof StandardError);
    }

    @Test
    @DisplayName("Deve cadastrar proposta")
    public void deveCadastrarProposta() {
        when(tracer.activeSpan()).thenReturn(span);
        when(propostaRepository.findByDocumento(any())).thenReturn(Optional.empty());
        when(request.toProposta()).thenReturn(proposta);
        when(request.getDocumento()).thenReturn(new String());
        ResponseEntity responseEntity = cadastrarPropostaController.cadastrarProposta(request, UriComponentsBuilder.newInstance());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertTrue(responseEntity.getHeaders().containsKey("Location"));
    }

}
