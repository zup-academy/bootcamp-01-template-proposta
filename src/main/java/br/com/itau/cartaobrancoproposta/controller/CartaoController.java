package br.com.itau.cartaobrancoproposta.controller;

import br.com.itau.cartaobrancoproposta.client.CartaoClient;
import br.com.itau.cartaobrancoproposta.error.ApiErrorException;
import br.com.itau.cartaobrancoproposta.model.Bloqueio;
import br.com.itau.cartaobrancoproposta.model.Cartao;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CartaoController {

    private final Logger logger = LoggerFactory.getLogger(CartaoController.class);
//1
    private final CartaoClient cartaoClient;

    private final EntityManager entityManager;

    @Value("${spring.application.name}")
    private String sistemaResponsavel;

    public CartaoController(CartaoClient cartaoClient, EntityManager entityManager) {
        this.cartaoClient = cartaoClient;
        this.entityManager = entityManager;
    }

    @PostMapping("/v1/cartoes/{id}/bloqueio")
    @Transactional
    public ResponseEntity<?> bloqueiaCartao(@PathVariable("id") String numeroCartao, HttpServletRequest request) {
        Query query = entityManager.createQuery("select u from Cartao u where u.numeroCartao =: value");
        query.setParameter("value", numeroCartao);

        if (query.getResultList().isEmpty()) { //1
            logger.error("Cartão não foi encontrado.");
            return ResponseEntity.notFound().build();
        }

        Cartao cartao = (Cartao) query.getResultList().get(0); //1

        Bloqueio bloqueio = new Bloqueio(request.getRemoteAddr(), request.getHeader("User-Agent")); //1

        Map<String, String> sistemaResponsavel = new HashMap<>();
        sistemaResponsavel.put("sistemaResponsavel", this.sistemaResponsavel);

        try { //1
            cartaoClient.bloqueiaCartao(numeroCartao, sistemaResponsavel);
        } catch (FeignException.UnprocessableEntity unprocessableEntity) { //1
            logger.error("Falha ao bloquear cartão com final {}.", cartao.getNumeroCartao().substring(24));
            throw new ApiErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "Falha ao bloquear cartão."); //1
        }

        cartao.carregaBloqueio(bloqueio);

        entityManager.merge(cartao);
        logger.error("Bloqueio id={} foi atrelado ao cartão com final {}.", cartao.getBloqueios().get(0).getId(),
                cartao.getNumeroCartao().substring(24));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
