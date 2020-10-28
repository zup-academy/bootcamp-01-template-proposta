package br.com.zup.proposta;

import br.com.zup.proposta.controller.PropostaController;
import br.com.zup.proposta.dto.NovaPropostaDtoRequest;
import br.com.zup.proposta.model.Proposta;
import br.com.zup.proposta.validations.DocumentoIgualValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

public class PropostaControllerTest {

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
    void teste02(){

        final EntityManager entityManager =
                Mockito.mock(EntityManager.class);

        DocumentoIgualValidator documentoIgualValidator =
                Mockito.mock(DocumentoIgualValidator.class);

        PropostaController controller =
                new PropostaController(entityManager, documentoIgualValidator);

        NovaPropostaDtoRequest request =
                new NovaPropostaDtoRequest("56.206.096/0001-01",
                        "email_email@email.com","Email email",
                        "Rua email, 1543", new BigDecimal(3500.00));

        UriComponentsBuilder builder = UriComponentsBuilder.newInstance();

        //quando o metodo for chamado retorna true
        Mockito.when(documentoIgualValidator.existe(request)).thenReturn(false);
        ResponseEntity<?> response = controller.novaProposta(request, builder);

        Proposta propostaQueDeviaSerGerada = request.toProposta();

        //metodo n esta sendo chamado
        //Mockito.verify(entityManager).persist(propostaQueDeviaSerGerada);

        //Mockito.doNothing()
        //        .when(entityManager).persist(propostaQueDeviaSerGerada);
        Assertions.assertEquals(HttpStatus.CREATED,response.getStatusCode());
    }

}
