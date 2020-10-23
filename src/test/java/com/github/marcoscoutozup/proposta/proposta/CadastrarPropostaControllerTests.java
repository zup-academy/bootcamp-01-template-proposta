package com.github.marcoscoutozup.proposta.proposta;

import com.github.marcoscoutozup.proposta.analisefinanceira.AnaliseFinanceiraService;
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

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CadastrarPropostaControllerTests {

    @Mock       //1
    private PropostaRepository propostaRepository;

    @Mock       //2
    private AnaliseFinanceiraService analiseFinanceiraService;

    @Mock
    private Logger logger;

    private UriComponentsBuilder builder;

            //3
    private CadastrarPropostaController cadastrarPropostaController;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        builder = UriComponentsBuilder.newInstance();
    }

    @Test
    @DisplayName("Não deve cadastrar proposta - Status code 422")
    public void naoDeveCadastrarProposta(){
        cadastrarPropostaController = new CadastrarPropostaController(propostaRepository, null);
                                                                                                //4
        when(propostaRepository.findByDocumento(any(String.class))).thenReturn(Optional.of(new Proposta()));
        ResponseEntity responseEntity = cadastrarPropostaController.cadastrarProposta(propostaDtoMock(), builder);
        Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        Assert.assertTrue(responseEntity.getBody() instanceof StandardError);
    }

    @Test
    @DisplayName("Deve cadastrar proposta - Status code 201")
    public void deveCadastrarProposta(){
        cadastrarPropostaController = new CadastrarPropostaController(propostaRepository, analiseFinanceiraService);
        when(propostaRepository.findByDocumento(any(String.class))).thenReturn(Optional.empty());
        ResponseEntity responseEntity = cadastrarPropostaController.cadastrarProposta(propostaDtoMock(), builder);
        Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assert.assertTrue(responseEntity.getHeaders().containsKey("Location"));
    }
            //5
    public PropostaRequest propostaDtoMock(){
        return new PropostaRequest("49258122038", "pessoa@email.com", "Pesssoa 1", "Rua um, 123", new BigDecimal(2000));
    }

}
