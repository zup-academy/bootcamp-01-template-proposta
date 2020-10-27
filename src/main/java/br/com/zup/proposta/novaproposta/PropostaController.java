package br.com.zup.proposta.novaproposta;

import br.com.zup.proposta.validation.BloqueiaPropostaIgualValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/propostas")
public class PropostaController {
    @PersistenceContext
    private EntityManager manager;
    private BloqueiaPropostaIgualValidator bloqueiaPropostaIgualValidator;

    public PropostaController(EntityManager manager,
                              BloqueiaPropostaIgualValidator bloqueiaPropostaIgualValidator) {
        super();
        this.manager = manager;
        this.bloqueiaPropostaIgualValidator = bloqueiaPropostaIgualValidator;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> criaProposta(@Valid @RequestBody NovaPropostaRequest request,
                                           UriComponentsBuilder uriComponentsBuilder) {
        if (!bloqueiaPropostaIgualValidator.isValid(request)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Proposta novaProposta = request.toModel();
        manager.persist(novaProposta);
        URI enderecoConsulta = uriComponentsBuilder.path("/propostas/{id}").build(novaProposta.getId());
        return ResponseEntity.created(enderecoConsulta).build();
    }
}
