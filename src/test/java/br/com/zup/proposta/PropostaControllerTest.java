package br.com.zup.proposta;

import br.com.zup.proposta.dto.AvaliaProposta;
import br.com.zup.proposta.controller.PropostaController;
import br.com.zup.proposta.dto.NovaPropostaDtoRequest;
import br.com.zup.proposta.model.Proposta;
import br.com.zup.proposta.validations.DocumentoIgualValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

public class PropostaControllerTest {

    Logger logger = LoggerFactory.getLogger(PropostaControllerTest.class);

    @Test
    @DisplayName("nao pode processar proposta com documento igual")
    void teste1(){

        DocumentoIgualValidator documentoIgualValidator =
                Mockito.mock(DocumentoIgualValidator.class);

        NovaPropostaDtoRequest request =
                new NovaPropostaDtoRequest("29.666.146/0001-52",
                        "email_email@email.com","Email email",
                        "Rua email, 1543", new BigDecimal(3500.00));

        Assertions.assertFalse(documentoIgualValidator.existe(request));
    }

    @Test
    @DisplayName("deve salvar se o documento está válido")
    void teste02() throws JsonProcessingException {

        EntityManager entityManager = Mockito.mock(EntityManager.class);

        DocumentoIgualValidator documentoIgualValidator = Mockito.mock(DocumentoIgualValidator.class);

        AvaliaProposta avaliaProposta = Mockito.mock(AvaliaProposta.class);

        PropostaController controller =
                new PropostaController(entityManager, documentoIgualValidator, avaliaProposta);

        NovaPropostaDtoRequest request =
                new NovaPropostaDtoRequest("56.206.096/0001-01",
                        "email_email@email.com","Email email",
                        "Rua email, 1543", new BigDecimal(3500.00));

        UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
        Mockito.when(documentoIgualValidator.existe(request)).thenReturn(false);
        ResponseEntity<?> response = controller.novaProposta(request, builder);
        Proposta propostaQueDeviaSerGerada = request.toProposta();

        Mockito.verify(entityManager).persist(propostaQueDeviaSerGerada);

        Assertions.assertEquals(HttpStatus.CREATED,response.getStatusCode());
    }

}
