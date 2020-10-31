package br.com.proposta.services;

import br.com.proposta.dtos.requests.NovoCartaoRequest;
import br.com.proposta.integracoes.IntegracaoApiCartoes;
import br.com.proposta.models.Cartao;
import br.com.proposta.models.Proposta;
import br.com.proposta.repositories.CartaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;


@Service
public class GerarCartaoService {

    /* total de pontos = 7 */

    private final EntityManager entityManager;

    /* @complexidade - classe criada no projeto */
    private final IntegracaoApiCartoes integracaoApiCartoes;

    /* @complexidade - classe criada no projeto */
    private final CartaoRepository cartaoRepository;

    private final Logger logger = LoggerFactory.getLogger(Proposta.class);


    public GerarCartaoService(EntityManager entityManager, IntegracaoApiCartoes integracaoApiCartoes, CartaoRepository cartaoRepository) {
        this.entityManager = entityManager;
        this.integracaoApiCartoes = integracaoApiCartoes;
        this.cartaoRepository = cartaoRepository;
    }


    @Transactional
    public void geraCartaoSegundoPlano(Proposta proposta){

        /* @complexidade - if */
        if(proposta.getCartao() == null){

                                            /* @complexidade - classe criada no projeto */
            boolean cartaoCriadoNaApiDeContas = integracaoApiCartoes.criarCartao(new NovoCartaoRequest(proposta)).getStatusCode() == HttpStatus.CREATED;

            /* @complexidade - if */
            if(cartaoCriadoNaApiDeContas){


                /* @complexidade - classe criada no projeto */
                Cartao cartao = new Cartao(proposta.getNome(), proposta);

                cartaoRepository.save(cartao);

                /* @complexidade - classe criada no projeto */
                proposta.associaCartao(cartao);

                entityManager.merge(proposta);


                logger.info("Cartão criado em segundo plano e associado com a proposta do cliente={}", proposta.getNome());

            }
        }
    }
}
