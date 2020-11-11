package br.com.proposta.services;

import br.com.proposta.dtos.requests.BloqueioRequest;
import br.com.proposta.dtos.responses.BloqueioResponse;
import br.com.proposta.entidades.Cartao;
import br.com.proposta.entidades.enums.StatusBloqueio;
import br.com.proposta.integracoes.IntegracaoApiCartoes;
import br.com.proposta.entidades.Bloqueio;
import br.com.proposta.entidades.Proposta;
import br.com.proposta.repositories.BloqueioRepository;
import br.com.proposta.repositories.CartaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class BloquearCartao {

    /* total de pontos = 9 */

    /* @complexidade - acoplamento contextual */
    private final IntegracaoApiCartoes integracaoApiCartoes;

    /* @complexidade - acoplamento contextual */
    private final BloqueioRepository bloqueioRepository;

    /* @complexidade - acoplamento contextual */
    private final CartaoRepository cartaoRepository;


    private final EntityManager entityManager;


    public BloquearCartao(IntegracaoApiCartoes integracaoApiCartoes, BloqueioRepository bloqueioRepository,
                          CartaoRepository cartaoRepository, EntityManager entityManager) {
        this.integracaoApiCartoes = integracaoApiCartoes;
        this.bloqueioRepository = bloqueioRepository;
        this.cartaoRepository = cartaoRepository;
        this.entityManager = entityManager;
    }

    private final Logger logger = LoggerFactory.getLogger(Proposta.class);


    public Bloqueio bloquear(String cartaoId, List<String> userAgentEInternetProtocol){

        /* @complexidade - classe criada no projeto + @complexidade - classe criada no projeto */
        var cartao = cartaoRepository.findByNumero(cartaoId);

        /* @complexidade - classe criada no projeto */
        var novoBloqueio = new Bloqueio(userAgentEInternetProtocol, cartao.get());
        novoBloqueio.associaCartao(cartao.get());
        bloqueioRepository.save(novoBloqueio);

        logger.info("Bloqueio gerado na API de propostas. Identificação do bloqueio={}", novoBloqueio.getId());

        return novoBloqueio;

    }

    @Transactional
    public BloqueioResponse avisarLegadoDoBloqueio(Cartao cartao){

        /* @complexidade - classe criada no projeto + @complexidade - classe criada no projeto */
        var bloqueioResponse = integracaoApiCartoes
                .avisarLegadoBloqueioDoCartao(cartao.getNumero(), new BloqueioRequest("api-cartoes"))
                .getBody();

        /* @complexidade - função como parâmetro */
        cartao.setStatus(StatusBloqueio.valueOf(bloqueioResponse.getResultado()));
        entityManager.merge(cartao);

        logger.info("Tentativa de bloqueio de cartão. Resposta do sistema legado = {} " +
                  " Titular do cartão = {}", bloqueioResponse.getResultado(), cartao.getTitular());

        return bloqueioResponse;


    }

}
