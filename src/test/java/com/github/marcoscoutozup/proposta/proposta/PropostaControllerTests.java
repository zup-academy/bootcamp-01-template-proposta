package com.github.marcoscoutozup.proposta.proposta;

import com.github.marcoscoutozup.proposta.exception.StandardError;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.Mockito.when;

public class PropostaControllerTests {

    @Mock
    private EntityManager entityManager;

    @Mock
    private Logger logger;

    @Mock
    private TypedQuery typedQuery;

    private UriComponentsBuilder builder;
    private PropostaController propostaController;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        builder = UriComponentsBuilder.newInstance();
    }

    @Test
    @DisplayName("Não deve cadastrar proposta - Status code 422")
    public void naoDeveCadastrarProposta(){
        propostaController = new PropostaController(entityManager, logger);
        when(entityManager.createNamedQuery("findPropostaByDocumento", Proposta.class)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Arrays.asList(""));
        ResponseEntity responseEntity = propostaController.cadastrarProposta(propostaDtoMock(), builder);
        Assert.assertTrue(responseEntity.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY));
        Assert.assertTrue(responseEntity.getBody() instanceof StandardError);
    }

    @Test
    @DisplayName("Deve cadastrar proposta - Status code 201")
    public void deveCadastrarProposta(){
        propostaController = new PropostaController(entityManager, logger);
        when(entityManager.createNamedQuery("findPropostaByDocumento", Proposta.class)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Arrays.asList());
        ResponseEntity responseEntity = propostaController.cadastrarProposta(propostaDtoMock(), builder);
        Assert.assertTrue(responseEntity.getStatusCode().equals(HttpStatus.CREATED));
        Assert.assertTrue(responseEntity.getHeaders().containsKey("Location"));
    }

    public PropostaDTO propostaDtoMock(){
        return new PropostaDTO("49258122038", "pessoa@email.com", "Pesssoa 1", "Rua um, 123", new BigDecimal(2000));
    }

}
