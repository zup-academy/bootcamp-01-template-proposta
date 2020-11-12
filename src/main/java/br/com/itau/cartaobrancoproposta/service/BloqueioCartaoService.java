package br.com.itau.cartaobrancoproposta.service;

import br.com.itau.cartaobrancoproposta.client.CartaoClient;
import br.com.itau.cartaobrancoproposta.error.ApiErrorException;
import br.com.itau.cartaobrancoproposta.model.Cartao;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BloqueioCartaoService {

    Logger logger = LoggerFactory.getLogger(BloqueioCartaoService.class);
//1
    private final CartaoClient cartaoClient;

    @Value("${spring.application.name}")
    private String sistemaResponsavel;

    public BloqueioCartaoService(CartaoClient cartaoClient) {
        this.cartaoClient = cartaoClient;
    }

    public boolean notificaBloqueioDoCartaoNoLegado(Cartao cartao) { //1

        Map<String, String> sistemaResponsavel = new HashMap<>();
        sistemaResponsavel.put("sistemaResponsavel", this.sistemaResponsavel);

        try { //1
            cartaoClient.bloqueiaCartao(cartao.getNumeroCartao(), sistemaResponsavel);
            return true;
        } catch (FeignException.UnprocessableEntity unprocessableEntity) { //1 1
            logger.error("Falha ao bloquear cartão com final {}.", cartao.getNumeroCartao().substring(24));
            throw new ApiErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "Falha ao bloquear cartão."); //1
        }
    }
}
