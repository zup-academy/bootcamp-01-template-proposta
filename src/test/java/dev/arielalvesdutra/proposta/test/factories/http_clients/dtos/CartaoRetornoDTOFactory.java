package dev.arielalvesdutra.proposta.test.factories.http_clients.dtos;

import dev.arielalvesdutra.proposta.entities.Proposta;
import dev.arielalvesdutra.proposta.http_clients.dtos.CartaoRetornoDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class CartaoRetornoDTOFactory {

    public static CartaoRetornoDTO cartaoCadastrado(Proposta proposta) {
        return new CartaoRetornoDTO()
                .setEmitidoEm(LocalDateTime.now().minusHours(2L))
                .setIdProposta(proposta.getId())
                .setId(UUID.randomUUID().toString())
                .setTitular(proposta.getNome())
                .setLimite(new BigDecimal("200.00"));
    }
}
