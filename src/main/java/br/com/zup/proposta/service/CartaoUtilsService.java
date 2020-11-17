package br.com.zup.proposta.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import br.com.zup.proposta.configs.exceptions.ApiException;
import br.com.zup.proposta.controllers.apiResponses.AssociaCarteiraResponse;
import br.com.zup.proposta.controllers.apiResponses.AvisoViagemResponse;
import br.com.zup.proposta.controllers.apiResponses.cartao.SolicitaBloqueioResponse;
import br.com.zup.proposta.controllers.form.AssociaCarteiraForm;
import br.com.zup.proposta.controllers.form.AssociaCarteiraRequest;
import br.com.zup.proposta.controllers.form.AvisoViagemForm;
import br.com.zup.proposta.controllers.form.SolicitaBloqueioForm;
import br.com.zup.proposta.model.cartao.Cartao;
import br.com.zup.proposta.model.cartao.CartaoAvisos;
import br.com.zup.proposta.model.cartao.CartaoBloqueio;
import br.com.zup.proposta.model.cartao.CarteiraDigital;
import br.com.zup.proposta.model.cartao.RecuperacaoSenha;
import br.com.zup.proposta.repositories.CartaoRepository;
import br.com.zup.proposta.service.feign.CartaoClient;

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
        String user = getLoggedUser(request);

        CartaoBloqueio bloqueio = new CartaoBloqueio(request.getRemoteAddr(), user, cartao);
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

        } catch (Exception e) {
            logger.warn("Nao foi possivel bloquear cartao no sistema legado. Cartao pode ja estar bloqueado.\n"
                    + "Desfazendo alteracoes.");
            throw new ApiException(
                    "Nao foi possivel bloquear cartao no sistema legado. Cartao pode ja estar bloqueado");
        }

        logger.info("Retornando cartao.");

        return cartao;
    }

    @Transactional
    public Cartao solicitaRecuperacaoSenha(String id, HttpServletRequest request) {
        logger.info("Iniciando solicitaRecuperacaoSenha()");

        Cartao cartao = getCartao(id);
        String user = getLoggedUser(request);

        logger.info("Adicionando pedido de recuperacao de senha ao cartao.");
        cartao.addRecuperacaoSenha(new RecuperacaoSenha(request.getRemoteAddr(), user, cartao));

        logger.info("Salvando alterações do cartao ao banco de dados.");
        manager.merge(cartao);

        logger.info("Retornando cartao.");
        return cartao;
    }

    @Transactional
    public Cartao cadastraAvisoDeViagem(String id, AvisoViagemForm form, HttpServletRequest request) {
        logger.info("Iniciando cadastraAvisoDeViagem()");

        Cartao cartao = getCartao(id);
        String user = getLoggedUser(request);

        logger.info("Adicionando aviso de viagem ao cartao.");
        cartao.addAvisos(new CartaoAvisos(form.getDataTermino(), form.getDestino(), user, 
            request.getRemoteAddr(), cartao));
        
        logger.info("Informando aviso de viagem ao sistema legado.");
        try {
            AvisoViagemResponse response = cartaoClient.solicitaViagem(id, form.toCartaoAvisos());

            if (response.isCriado()) {
                logger.info("Salvando alterações do cartão no banco de dados");
                
                manager.merge(cartao);

                logger.info("Aviso de viagem registrado com sucesso.");
            }
        } catch(Exception e) {
            logger.warn("Erro inesperado ao informar o aviso de viagem ao sistema legado.\n" +
                "Desfazendo alterações.");
            throw new ApiException("Erro inesperado ao informar o aviso de viagem ao sistema legado.");
        }

        logger.info("Retornando cartao.");
        return cartao;
    }

    @Transactional
    public Cartao associaCarteira(String id, AssociaCarteiraForm form, HttpServletRequest request) {
        logger.info("Iniciando associaCarteira()");

        Cartao cartao = getCartao(id);

        try {
            AssociaCarteiraResponse response = cartaoClient.associaCarteira(id, new AssociaCarteiraRequest(cartao.getProposta().getEmail(), form.getCarteira()));

            if (response.isAssociada()) {
                cartao.addCarteiras(new CarteiraDigital(cartao.getProposta().getEmail(), form.getCarteira(), cartao));

                manager.merge(cartao);
            }
        } catch (Exception e) {
            logger.warn("Erro ao associar carteira digital no sistema legado.\nDesfazendo alterações.");
            throw new ApiException("Erro ao associar carteira digital no sistema legado.");
        }

        return cartao;
    }

    private Cartao getCartao(String id) {
        logger.info("Solicitando cartao do banco de dados.");

        Cartao cartao = cartaoRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(String.format("Cartão de id %s não encontrado", id)));

        logger.info("Cartao de id {} encontrado", id);

        return cartao;
    }

    private String getLoggedUser(HttpServletRequest request) {
        logger.info("Coletando informacoes de usuario logado.");

        Jwt user = (Jwt)SecurityContextHolder.getContext().getAuthentication().getCredentials();

        return user.getClaim("user_name");
    }

}
