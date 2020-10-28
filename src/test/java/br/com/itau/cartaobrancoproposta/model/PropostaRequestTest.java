package br.com.itau.cartaobrancoproposta.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class PropostaRequestTest {

    @Test
    void requestToModel() {
        PropostaRequest propostaRequest = new PropostaRequest("330.547.310-06", "raphael@gmail.com",
                "Raphael", "Rua 1", BigDecimal.valueOf(1000));

        Proposta proposta = propostaRequest.toModel();

        Assertions.assertEquals("330.547.310-06", proposta.getDocumento());
        Assertions.assertEquals("raphael@gmail.com", proposta.getEmail());
        Assertions.assertEquals("Raphael", proposta.getNome());
        Assertions.assertEquals("Rua 1", proposta.getEndereco());
        Assertions.assertEquals(BigDecimal.valueOf(1000), proposta.getSalario());
    }
}
