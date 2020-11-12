package br.com.proposta.resources;

import br.com.proposta.dtos.requests.PropostaRequest;
import br.com.proposta.entidades.Proposta;
import br.com.proposta.repositories.PropostaRepository;
import br.com.proposta.services.AvaliaProposta;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;


@RestController
@RequestMapping("/api/propostas")
public class PropostaResource {

    /* total de pontos = 8 */

    /* @complexidade (1) - acoplamento contextual */
    private final AvaliaProposta avaliaProposta;

    /* @complexidade (1) - acoplamento contextual */
    private final PropostaRepository propostaRepository;


    private final Logger logger = LoggerFactory.getLogger(Proposta.class);

    private final EntityManager entityManager;


    public PropostaResource(AvaliaProposta avaliaProposta, PropostaRepository propostaRepository,
                            EntityManager entityManager) {

        this.avaliaProposta = avaliaProposta;
        this.propostaRepository = propostaRepository;
        this.entityManager = entityManager;
    }


    @PostMapping
    @Transactional
    public ResponseEntity<?> novaProposta(@RequestBody @Valid PropostaRequest propostaRequest, UriComponentsBuilder uriComponentsBuilder) throws JsonProcessingException {


            /* @complexidade (4) - classe criada no projeto + branch + função como parâmetro */
            var novaProposta = propostaRequest.toModel();
            if(novaProposta.ehUnica(propostaRepository)){

                propostaRepository.save(novaProposta);
                novaProposta.atualizaStatusElegibilidade(avaliaProposta.retornarAvaliacao(novaProposta));

                entityManager.merge(novaProposta);

                logger.info("Proposta documento={} salário={} criada com sucesso!", novaProposta.getIdentificacao(), novaProposta.getSalario());

                return ResponseEntity
                        .created(uriComponentsBuilder.path("/propostas/{id}")
                        .buildAndExpand(novaProposta.getId()).toUri()).build();

            }

            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
    }


    @GetMapping("/{propostaId}")
    public ResponseEntity<?> acompanharProposta(@PathVariable String propostaId){

        /* @complexidade (2) - classe criada no projeto + branch */
        var propostaAcompanhamento = propostaRepository.findById(propostaId);
        if(propostaAcompanhamento.isPresent()){

            logger.info("Acompanhamento da proposta do cliente={}", propostaAcompanhamento.get().getNome());

            return ResponseEntity.ok(propostaAcompanhamento);

        }

        logger.info("Proposta não encontrada");
        return ResponseEntity.notFound().build();

    }
}
