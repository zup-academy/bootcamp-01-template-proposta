package br.com.zup.proposta.service;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.zup.proposta.controllers.apiResponses.cartao.CartaoResponse;
import br.com.zup.proposta.model.Proposta;
import br.com.zup.proposta.model.cartao.Cartao;
import br.com.zup.proposta.repositories.PropostaRepository;
import br.com.zup.proposta.service.feign.CartaoClient;

@Service
public class CartaoService {
    
    private static final Logger logger = LoggerFactory.getLogger(CartaoService.class);

    @Autowired
    private PropostaRepository propostaRepository;
    @Autowired
    private CartaoClient cartaoClient;

    @PersistenceContext
    private EntityManager manager;

    @Async("cartaoAsync")
    @Transactional
    public CompletableFuture<?> verificarCartao() throws InterruptedException {
        logger.info("Iniciando verificarCartao() Async");

        Collection<Proposta> propostas = propostaRepository.findByCartaoNaoCriado();

        logger.info("Numero de propostas encontradas: {}", propostas.size());

        propostas.forEach(proposta -> {
            
            CartaoResponse cartaoCriado = cartaoClient.solicitaCartao(proposta.getId());

            if (cartaoCriado != null) {
                try {
                    logger.info("Cartão encontrado para proposta {}", proposta.getId());
                    logger.info("Cartao recebido: {}", cartaoCriado.toString());
    
                    Cartao cartao = cartaoCriado.toCartao(proposta);
    
                    logger.info("Objeto de cartao criado.");
    
                    proposta.setCartaoCriado(true);
                    proposta.setCartao(cartao);
                    logger.info("Cartão persistido para proposta");
                    manager.persist(cartao);
                    logger.info("Cartão salvo no banco de dados");
                    manager.merge(proposta);
                    logger.info("Proposta atualizada no banco de dados");
                    //manager.joinTransaction();
                    //manager.flush();
                } catch (Exception e) {
                    logger.info("Deu ruim, proposta {}", proposta.getId());
                    logger.info(e.getCause().toString());
                }
            }
        });

        logger.info("verificaCartao() finalizado.");
        return CompletableFuture.completedFuture(propostas);
    }
}
