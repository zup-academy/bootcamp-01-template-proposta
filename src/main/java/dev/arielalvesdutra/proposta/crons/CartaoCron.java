package dev.arielalvesdutra.proposta.crons;

import dev.arielalvesdutra.proposta.entities.Cartao;
import dev.arielalvesdutra.proposta.entities.Proposta;
import dev.arielalvesdutra.proposta.services.CartaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class CartaoCron {

    private final Logger logger = LoggerFactory.getLogger(CartaoCron.class);

    @Autowired
    private CartaoService cartaoService;

    /**
     * Sincroniza novos cartões emitidos no legado (Serviço de Cartões) com o sistema.
     *
     * Para cada {@link Proposta} elegivel que não possui {@link Cartao}, busca o
     * Cartão no Serviço de Cartões pelo ID da Proposta.
     *
     * Cada novo Cartão retornado, será registrado no sistema vinculado a Proposta.
     *
     * Cron do Spring:
     */
    @Scheduled(cron = "${cron.cartao.cron:1 1 * * * ?}")
    public void sincronizaNovosCartoesEmitidosNoServicoDeCartoes() {
        logger.info("Iniciando a sincronização de novos cartões emitidos no Serviço de Cartões.");
        cartaoService.sincronizaNovosCartoesEmitidosNoServicoDeCartoes();
        logger.info("Completando a sincronização de novos cartões emitidos no Serviço de Cartões.");
    }
}
