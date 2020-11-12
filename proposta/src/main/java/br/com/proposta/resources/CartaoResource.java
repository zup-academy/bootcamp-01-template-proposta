package br.com.proposta.resources;

import br.com.proposta.entidades.enums.StatusAvaliacaoProposta;
import br.com.proposta.entidades.Proposta;
import br.com.proposta.services.GerarCartao;
import br.com.proposta.repositories.PropostaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/cartoes")
public class CartaoResource {

    /* total de pontos = 5 */

    private final Logger logger = LoggerFactory.getLogger(Proposta.class);

    /* @complexidade - acoplamento contextual */
    private final GerarCartao gerarCartao;

    /* @complexidade - acoplamento contextual */
    private final PropostaRepository propostaRepository;


    public CartaoResource(GerarCartao gerarCartao, PropostaRepository propostaRepository) {
        this.gerarCartao = gerarCartao;
        this.propostaRepository = propostaRepository;
    }


    @Async
    @PostMapping
    @Scheduled(initialDelay=1000, fixedRate=5000)
    public void geraCartoesAsync(){

        /* @complexidade - utilizando classe criada no projeto */
        var propostasAceitasSemCartao =
                propostaRepository.findByStatusAndCartaoNull(StatusAvaliacaoProposta.ELEGIVEL);

        /* @complexidade - iterando coleção criada no projeto  */
        propostasAceitasSemCartao.forEach(proposta -> {

            /* @complexidade - utilizando classe criada no projeto */
            gerarCartao.geraCartaoSegundoPlano(proposta);

            logger.info("Cartão gerado na API de cartões. Nome do titular={}", proposta.getNome());

        });
    }
}
