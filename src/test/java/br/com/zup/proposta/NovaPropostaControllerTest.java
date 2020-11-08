package br.com.zup.proposta;

import br.com.zup.proposta.dao.ExecutorTransacao;
import br.com.zup.proposta.services.AvaliaProposta;
import br.com.zup.proposta.controller.NovaPropostaController;
import br.com.zup.proposta.validations.DocumentoIgualValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class NovaPropostaControllerTest {

    private DocumentoIgualValidator documentoIgualValidator =
            Mockito.mock(DocumentoIgualValidator.class);
    private AvaliaProposta avaliaProposta = Mockito.mock(AvaliaProposta.class);
    private ExecutorTransacao executorTransacao = Mockito.mock(ExecutorTransacao.class);
    //private NovaPropostaController controller =
    //        new NovaPropostaController(documentoIgualValidator, avaliaProposta,executorTransacao);

    @Test
    @DisplayName("nao pode processar proposta com documento igual")
    void teste1(){

        /*NovaPropostaRequest request =
                new NovaPropostaRequest("29.666.146/0001-52",
                        "email_email@email.com","Email email",
                        "Rua email, 1543", new BigDecimal(3500.00));

        Assertions.assertFalse(documentoIgualValidator.existe(request));*/
    }

    @Test
    @DisplayName("deve salvar se o documento está válido")
    void teste02() {

        /*NovaPropostaRequest request =
                new NovaPropostaRequest("29.666.146/0001-52",
                        "email_email@email.com","Email email",
                        "Rua email, 1543", new BigDecimal(3500.00));

        UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
        Mockito.when(documentoIgualValidator.existe(request)).thenReturn(false);
        //ResponseEntity<?> response = controller.novaProposta(request, builder);

        Proposta propostaQueDeviaSerGerada = request.toProposta();*/
        //Mockito.verify(executorTransacao).salvaEComita(propostaQueDeviaSerGerada);
        //Assertions.assertEquals(HttpStatus.CREATED,response.getStatusCode());
    }

}
