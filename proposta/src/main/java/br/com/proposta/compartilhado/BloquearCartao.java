package br.com.proposta.compartilhado;

import br.com.proposta.dtos.requests.BloqueioRequest;
import br.com.proposta.dtos.responses.BloqueioResponse;
import br.com.proposta.entidades.Cartao;
import br.com.proposta.entidades.Enums.StatusBloqueio;
import br.com.proposta.integracoes.IntegracaoApiCartoes;
import br.com.proposta.entidades.Bloqueio;
import br.com.proposta.entidades.Proposta;
import br.com.proposta.repositories.BloqueioRepository;
import br.com.proposta.repositories.CartaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class BloquearCartao {

    /* total de pontos = 7 */

    /* @complexidade - acoplamento contextual */
    private final IntegracaoApiCartoes integracaoApiCartoes;

    /* @complexidade - acoplamento contextual */
    private BloqueioRepository bloqueioRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private EntityManager entityManager;


    public BloquearCartao(IntegracaoApiCartoes integracaoApiCartoes,
                          BloqueioRepository bloqueioRepository) {
        this.integracaoApiCartoes = integracaoApiCartoes;
        this.bloqueioRepository = bloqueioRepository;
    }

    private final Logger logger = LoggerFactory.getLogger(Proposta.class);


    public Bloqueio bloquear(String cartaoId, List<String> userAgentEInternetProtocol){


        /* @complexidade - classe criada no projeto + @complexidade - classe criada no projeto */
        var cartao = cartaoRepository.findByNumero(cartaoId);


        /* @complexidade - classe criada no projeto */
        var novoBloqueio = new Bloqueio(userAgentEInternetProtocol, cartaoId);

        bloqueioRepository.save(novoBloqueio);

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

        logger.info("Tentativa de bloqueio de cartão. Resultado={}", bloqueioResponse.getResultado());

        return bloqueioResponse;


    }

}
