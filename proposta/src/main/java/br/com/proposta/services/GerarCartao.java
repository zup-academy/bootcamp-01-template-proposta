package br.com.proposta.services;

import br.com.proposta.integracoes.IntegracaoApiCartoes;
import br.com.proposta.entidades.Cartao;
import br.com.proposta.entidades.Proposta;
import br.com.proposta.repositories.CartaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;


@Service
public class GerarCartao {

    /* total de pontos = 7 */

    private final EntityManager entityManager;

    /* @complexidade - acoplamento contextual */
    private final IntegracaoApiCartoes integracaoApiCartoes;

    /* @complexidade - acoplamento contextual */
    private final CartaoRepository cartaoRepository;

    private final Logger logger = LoggerFactory.getLogger(Proposta.class);


    public GerarCartao(EntityManager entityManager, IntegracaoApiCartoes integracaoApiCartoes, CartaoRepository cartaoRepository) {
        this.entityManager = entityManager;
        this.integracaoApiCartoes = integracaoApiCartoes;
        this.cartaoRepository = cartaoRepository;
    }

    @Transactional
    public void geraCartaoSegundoPlano(Proposta proposta){

        /* @complexidade - classe criada no projeto */
        var cartaoGeradoPeloLegado = integracaoApiCartoes.buscarCartao(proposta.getId());

        /* @complexidade - if */
        if(cartaoGeradoPeloLegado.getStatusCode() == HttpStatus.OK){

                /* @complexidade - classe criada no projeto */
                var dadosCartao = cartaoGeradoPeloLegado.getBody();

                /* @complexidade - classe criada no projeto */
                Cartao cartao = new Cartao(dadosCartao.getId(), proposta.getNome(), proposta);

                cartaoRepository.save(cartao);

                /* @complexidade - classe criada no projeto */
                proposta.associaCartao(cartao);

                entityManager.merge(proposta);

                logger.info("Cartão criado com sucesso e associado com a proposta do cliente={}", proposta.getNome());

        }
    }
}
