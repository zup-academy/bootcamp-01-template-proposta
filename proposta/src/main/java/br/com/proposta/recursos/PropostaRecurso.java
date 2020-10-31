package br.com.proposta.recursos;

import br.com.proposta.transferenciadados.requisicoes.RequisicaoProposta;
import br.com.proposta.transferenciadados.respostas.RespostaProposta;
import br.com.proposta.entidades.Proposta;
import br.com.proposta.repositorios.PropostaRepository;
import br.com.proposta.compartilhado.AvaliaProposta;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;


@RestController
@RequestMapping("/api/propostas")
public class PropostaRecurso {

    /* total de pontos = 8 */


    /* @complexidade - classe criada no projeto */
    private final AvaliaProposta avaliaProposta;

    /* @complexidade - classe criada no projeto */
    private final PropostaRepository propostaRepository;

    private final Logger logger = LoggerFactory.getLogger(Proposta.class);

    private final EntityManager entityManager;


    public PropostaRecurso(AvaliaProposta avaliaProposta, PropostaRepository propostaRepository,
                           EntityManager entityManager) {

        this.avaliaProposta = avaliaProposta;
        this.propostaRepository = propostaRepository;
        this.entityManager = entityManager;
    }


    @PostMapping
    @Transactional
    public ResponseEntity<?> novaProposta(@RequestBody @Valid RequisicaoProposta requisicaoProposta, UriComponentsBuilder uriComponentsBuilder) throws JsonProcessingException {


            /* @complexidade - classe criada no projeto */
            var novaProposta = requisicaoProposta.toModel();


            /* @complexidade - if */
            if(novaProposta.ehUnica(propostaRepository)){


                propostaRepository.save(novaProposta);

                /* @complexidade - função como parâmetro  */
                novaProposta.atualizaStatusElegibilidade(avaliaProposta.retornarAvaliacao(novaProposta));

                entityManager.merge(novaProposta);

                logger.info("Proposta documento={} salário={} criada com sucesso!", novaProposta.getIdentificacao(), novaProposta.getSalario());

                return ResponseEntity
                        .created(uriComponentsBuilder.path("/propostas/{id}").buildAndExpand(novaProposta.getId()).toUri()).build();

            }

            /* @complexidade - exceção no fluxo do programa */
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Número de identificação já existe");

    }


    @GetMapping
    public ResponseEntity<?> acompanharProposta(@PathVariable String id){

        /* @complexidade - classe criada no projeto */
        var propostaAcompanhamento =
                propostaRepository.findById(id).get();

        logger.info("Acompanhamento da proposta do cliente={}",
                propostaAcompanhamento.getNome());

        /* @complexidade - classe criada no projeto  */
        return ResponseEntity.ok(new RespostaProposta(propostaAcompanhamento));

    }
}
