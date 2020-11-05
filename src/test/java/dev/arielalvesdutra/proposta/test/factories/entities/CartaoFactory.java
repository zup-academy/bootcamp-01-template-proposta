package dev.arielalvesdutra.proposta.test.factories.entities;

import dev.arielalvesdutra.proposta.entities.Cartao;
import dev.arielalvesdutra.proposta.entities.Proposta;

import static dev.arielalvesdutra.proposta.test.factories.http_clients.dtos.CartaoRetornoDTOFactory.cartaoCadastrado;

public class CartaoFactory {

    public static Cartao cartaoValido(Proposta proposta) {
        var cartao = cartaoCadastrado(proposta).paraEntidade();

        proposta.setCartao(cartao);

        return cartao;
    }
}
