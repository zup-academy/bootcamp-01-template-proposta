package br.com.zup.proposta.proposta;

import br.com.zup.proposta.analiseproposta.AvaliaProposta;
import br.com.zup.proposta.analiseproposta.StatusAvaliacaoProposta;
import feign.FeignException;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    private final PropostaRepository repository;
    private final EntityManager entityManager;
    private final AvaliaProposta avaliaProposta;
    private final Tracer tracer;

    private final Logger logger = LoggerFactory.getLogger(PropostaController.class);

    public PropostaController(PropostaRepository repository, EntityManager entityManager,
                              AvaliaProposta avaliaProposta, Tracer tracer) {
        this.repository = repository;
        this.entityManager = entityManager;
        this.avaliaProposta = avaliaProposta;
        this.tracer = tracer;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> criaProposta(@Valid @RequestBody NovaPropostaRequest request,
                                          UriComponentsBuilder uriComponentsBuilder,
                                          Authentication authentication) {
        Span activeSpan = tracer.activeSpan();
        activeSpan.setTag("user.email", request.getEmail());
        activeSpan.setBaggageItem("usuario.email", request.getEmail());
        activeSpan.log("Realizando cadastro de proposta do usuário");

        if (repository.findByDocumento(request.getDocumento()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Proposta novaProposta = request.toModel();

        entityManager.persist(novaProposta);

        try {
            StatusAvaliacaoProposta avaliacao = avaliaProposta.executa(novaProposta);
            novaProposta.atualizaStatus(avaliacao);
        } catch (FeignException.UnprocessableEntity e) {
            novaProposta.atualizaStatus(StatusAvaliacaoProposta.NAO_ELEGIVEL);
        }

        entityManager.merge(novaProposta);

        URI enderecoConsulta = uriComponentsBuilder.path("/propostas/{id}").build(novaProposta.getId());

        logger.info("Proposta de Documento = {} e Status = {} criada com sucesso!",
                    novaProposta.getDocumento(), novaProposta.getStatusAvaliacao());

        return ResponseEntity.created(enderecoConsulta).build();
    }
}
