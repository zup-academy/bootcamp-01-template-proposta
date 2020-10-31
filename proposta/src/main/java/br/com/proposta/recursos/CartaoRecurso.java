package br.com.proposta.recursos;

import br.com.proposta.entidades.Enums.StatusAvaliacaoProposta;
import br.com.proposta.entidades.Proposta;
import br.com.proposta.compartilhado.GerarCartao;
import br.com.proposta.repositorios.PropostaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/cartoes")
public class CartaoRecurso {

    /* total de pontos = 5 */

    private final Logger logger = LoggerFactory.getLogger(Proposta.class);

    /* @complexidade - classe criada no projeto */
    private final GerarCartao gerarCartao;

    /* @complexidade - classe criada no projeto */
    private final PropostaRepository propostaRepository;


    public CartaoRecurso(GerarCartao gerarCartao, PropostaRepository propostaRepository) {
        this.gerarCartao = gerarCartao;
        this.propostaRepository = propostaRepository;
    }


    @PostMapping
    @Async
    @Scheduled(initialDelay=1000, fixedRate=5000)
    public void geraCartoesAsync(){

        /* @complexidade - utilizando classe criada no projeto */
        var propostasAceitasSemCartao =
                propostaRepository.findByStatusAndCartaoNull(StatusAvaliacaoProposta.ELEGIVEL);

        /* @complexidade - iterando coleção criada no projeto  */
        propostasAceitasSemCartao.forEach(proposta -> {

            /* @complexidade - utilizando classe criada no projeto */
            gerarCartao.geraCartaoSegundoPlano(proposta);

            logger.info("Cartão gerado no serviço de cartões referente à proposta: nome do cliente={}", proposta.getNome());
        });
    }
}
