package br.com.zup.proposta.novaproposta;

import br.com.zup.proposta.integracao.AvaliaProposta;
import br.com.zup.proposta.integracao.ExecutorTransacao;
import br.com.zup.proposta.integracao.StatusAvaliacaoProposta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    private final PropostaRepository repository;
    private final ExecutorTransacao executorTransacao;
    private final AvaliaProposta avaliaProposta;

    public PropostaController(PropostaRepository repository,
                              ExecutorTransacao executorTransacao, AvaliaProposta avaliaProposta) {
        this.repository = repository;
        this.executorTransacao = executorTransacao;
        this.avaliaProposta = avaliaProposta;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> criaProposta(@Valid @RequestBody NovaPropostaRequest request,
                                           UriComponentsBuilder uriComponentsBuilder) {
        if (repository.findByDocumento(request.getDocumento()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Proposta novaProposta = request.toModel();
        executorTransacao.salvaEComita(novaProposta);
        StatusAvaliacaoProposta avaliacao = avaliaProposta.executa(novaProposta);
        novaProposta.atualizaStatus(avaliacao);
        executorTransacao.atualizaEComita(novaProposta);
        URI enderecoConsulta = uriComponentsBuilder.path("/propostas/{id}").build(novaProposta.getId());
        return ResponseEntity.created(enderecoConsulta).build();
    }
}
