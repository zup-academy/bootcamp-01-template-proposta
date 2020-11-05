package dev.arielalvesdutra.proposta.services;

import dev.arielalvesdutra.proposta.entities.Cartao;
import dev.arielalvesdutra.proposta.entities.Proposta;
import dev.arielalvesdutra.proposta.http_clients.CartaoHttpClient;
import dev.arielalvesdutra.proposta.repositories.CartaoRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class CartaoService {

    @Value("${spring.profiles.active:desconhecido}")
    private String perfilAtivo;

    private final Logger logger = LoggerFactory.getLogger(CartaoService.class);

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private PropostaService propostaService;

    @Autowired
    private CartaoHttpClient cartaoHttpClient;

    public CartaoService(
            CartaoRepository cartaoRepository,
            PropostaService propostaService,
            CartaoHttpClient cartaoHttpClient) {

        this.cartaoRepository = cartaoRepository;
        this.propostaService = propostaService;
        this.cartaoHttpClient = cartaoHttpClient;
    }

    public Cartao buscaPeloId(String cartaoId) {
        return cartaoRepository.findById(cartaoId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Cartão de ID " + cartaoId + " não localizado!"));
    }

    public Cartao buscaPelaPropostaId(String propostaId) {
        return cartaoRepository.findByPropostaId(propostaId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Cartão com Proposta de ID " + propostaId + " não localizado!"));
    }

    /**
     * Sincroniza novos cartões emitidos no legado (Serviço de Cartões) com o sistema.
     *
     * Para cada {@link Proposta} elegivel que não possui {@link Cartao}, busca o
     * Cartão no Serviço de Cartões pelo ID da Proposta.
     *
     * Cada novo Cartão retornado, será registrado no sistema vinculado a Proposta.
     */
    @Transactional
    public void sincronizaNovosCartoesEmitidosNoServicoDeCartoes() {
        logger.info("Buscando propostas elegíveis que não possuem cartão.");
        List<Proposta> propostasElegiveisESemCartao = propostaService.buscaTodasElegiveisESemCartao();
        logger.info("Propostas elegíveis que não possuem cartão buscadas.");
        propostasElegiveisESemCartao.forEach(proposta -> {
            try {
                logger.info("Buscando cartão para a proposta de ID {}.", proposta.getId());
                var cartaoRetornoDTO = cartaoHttpClient.buscaCartao(proposta.getId());

                var cartaoParaSalvar = cartaoRetornoDTO.paraEntidade();

                proposta.setCartao(cartaoParaSalvar);

                logger.info("Cartão de ID {} salvo para prosta de ID {}.", cartaoParaSalvar.getId(), proposta.getId());
            } catch (FeignException e) {
                logger.warn("Ainda não há cartão para a proposta de ID {}.", proposta.getId());
            }
        });
    }

    @Transactional
    public void removeTodos() {
        if (!perfilAtivo.equals("test")) {
            logger.warn("Ação não permitida!");
            throw new UnsupportedOperationException("Ação não permitida!");
        }

        logger.info("Removendo todos os registros.");
        cartaoRepository.deleteAll();
    }
}
