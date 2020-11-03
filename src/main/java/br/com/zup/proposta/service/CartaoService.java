package br.com.zup.proposta.service;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.zup.proposta.controllers.apiResponses.cartao.CartaoResponse;
import br.com.zup.proposta.model.Proposta;
import br.com.zup.proposta.repositories.PropostaRepository;
import br.com.zup.proposta.service.feign.CartaoClient;

@Service
public class CartaoService {
    
    private static final Logger logger = LoggerFactory.getLogger(CartaoService.class);

    @Autowired
    private PropostaRepository repository;
    @Autowired
    private CartaoClient cartaoClient;

    @Async("cartaoAsync")
    public CompletableFuture<?> verificarCartao() throws InterruptedException {
        logger.info("Iniciando verificarCartao() Async");

        Collection<Proposta> propostas = repository.findByCartaoNaoCriado();

        logger.info("Numero de propostas encontradas: {}", propostas.size());

        propostas.forEach(proposta -> {
            
            CartaoResponse cartaoCriado = cartaoClient.solicitaCartao(proposta.getId());

            if (cartaoCriado != null) {
                proposta.setCartaoCriado(true);
                proposta.setCartao(cartaoCriado.getId());
                repository.save(proposta);
            }
        });

        return CompletableFuture.completedFuture(propostas);
    }
}
