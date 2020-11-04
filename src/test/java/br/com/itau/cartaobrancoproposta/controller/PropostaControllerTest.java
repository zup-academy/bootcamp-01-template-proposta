package br.com.itau.cartaobrancoproposta.controller;

import br.com.itau.cartaobrancoproposta.error.ApiErrorException;
import br.com.itau.cartaobrancoproposta.model.Proposta;
import br.com.itau.cartaobrancoproposta.model.PropostaRequest;
import br.com.itau.cartaobrancoproposta.validator.VerificaPropostaMesmoSolicitante;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

public class PropostaControllerTest {

    @Test
    @DisplayName("nao pode processar proposta com documento igual")
    public void naoDeveRetornarPropostaComDocumentoIgual() {
        EntityManager manager = Mockito.mock(EntityManager.class);
        VerificaPropostaMesmoSolicitante verificaProposta = Mockito.mock(VerificaPropostaMesmoSolicitante.class);

        PropostaController propostaController = new PropostaController(null, verificaProposta, null);
        PropostaRequest propostaRequest = new PropostaRequest("330.547.310-06", "raphael@gmail.com", "Raphael", "Rua 1", BigDecimal.valueOf(1000));
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance();

        Assertions.assertThrows(ApiErrorException.class, () -> propostaController.criaProposta(propostaRequest, builder));
    }

//    @Test
    @DisplayName("deve criar a proposta se documento válido")
    public void deveCriarPropostaSeDocumentoValido() {
        EntityManager manager = Mockito.mock(EntityManager.class);
        VerificaPropostaMesmoSolicitante verificaProposta = Mockito.mock(VerificaPropostaMesmoSolicitante.class);

        PropostaController propostaController = new PropostaController(null, verificaProposta, null);
        PropostaRequest propostaRequest = new PropostaRequest("330.547.310-06", "raphael@gmail.com", "Raphael", "Rua 1", BigDecimal.valueOf(1000));
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance();

        Mockito.when(verificaProposta.estaValido(propostaRequest.getDocumento())).thenReturn(true);

        ResponseEntity<?> responseEntity = propostaController.criaProposta(propostaRequest, builder);

        Proposta proposta = propostaRequest.toModel();

        Mockito.verify(manager).persist(proposta);

        Assertions.assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
    }
}
