package br.com.zup.proposta.service;

import java.security.Principal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zup.proposta.configs.exceptions.ApiException;
import br.com.zup.proposta.controllers.apiResponses.cartao.SolicitaBloqueioResponse;
import br.com.zup.proposta.controllers.form.SolicitaBloqueioForm;
import br.com.zup.proposta.model.cartao.Cartao;
import br.com.zup.proposta.model.cartao.CartaoBloqueio;
import br.com.zup.proposta.model.cartao.RecuperacaoSenha;
import br.com.zup.proposta.repositories.CartaoRepository;
import br.com.zup.proposta.service.feign.CartaoClient;
import feign.FeignException.UnprocessableEntity;

@Service
public class CartaoUtilsService {
    
    private static final Logger logger = LoggerFactory.getLogger(CartaoService.class);

    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private CartaoClient cartaoClient;

    @PersistenceContext
    private EntityManager manager;

    @Transactional
	public Cartao solicitaBloqueio(String id, HttpServletRequest request) {
        logger.info("Iniciando solicitaBloqueio()");

        Cartao cartao = getCartao(id);
        Principal principal = getLoggedPrincipal(request);

        CartaoBloqueio bloqueio = new CartaoBloqueio(request.getRemoteAddr(), principal.getName(), cartao);
        cartao.addBloqueios(bloqueio);

        logger.info("Solicitacao de bloqueio adicionada ao cartao.");

        
        try {
            logger.info("Solicitando bloqueio para o sistema legado.");
            SolicitaBloqueioResponse response = cartaoClient.solicitaBloqueio(id, new SolicitaBloqueioForm("proposta"));
            
            if (response.isBloqueado()) {
                logger.info("Cartao bloqueado com sucesso.");
                logger.info("Atualizando cartao no banco de dados.");
    
                manager.merge(cartao);
            } 
            
        } catch (UnprocessableEntity e) {
            if (e.status() == 422) {
                logger.warn("Nao foi possivel bloquear cartao no sistema legado. Cartao pode ja estar bloqueado.\n" +
                    "Desfazendo alteracoes.");
                throw new ApiException("Nao foi possivel bloquear cartao no sistema legado. Cartao pode ja estar bloqueado");    
            }
        }

        logger.info("Retornando cartao.");

        return cartao;
    }
    
    @Transactional
    public Cartao solicitaRecuperacaoSenha(String id, HttpServletRequest request) {
        logger.info("Iniciando solicitaRecuperacaoSenha()");

        Cartao cartao = getCartao(id);
        Principal principal = getLoggedPrincipal(request);

        logger.info("Adicionando pedido de recuperacao de senha ao cartao.");
        cartao.addRecuperacaoSenha(new RecuperacaoSenha(request.getRemoteAddr(), principal.getName(), cartao));

        logger.info("Salvando alterações do cartao ao banco de dados.");
        manager.merge(cartao);

        logger.info("Retornando cartao.");
        return cartao;
    }

    private Cartao getCartao(String id) {
        logger.info("Solicitando cartao do banco de dados.");

		Cartao cartao = cartaoRepository.findById(id).orElseThrow(
            () -> new IllegalStateException(String.format("Cartão de id %s não encontrado", id)));

        logger.info("Cartao de id {} encontrado", id);

        return cartao;
    }

    private Principal getLoggedPrincipal(HttpServletRequest request) {
        logger.info("Coletando informacoes de usuario logado.");

        return request.getUserPrincipal();
    }

}
