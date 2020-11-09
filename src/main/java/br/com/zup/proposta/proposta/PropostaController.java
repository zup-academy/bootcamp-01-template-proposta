package br.com.zup.proposta.proposta;

import br.com.zup.proposta.analiseproposta.AvaliaProposta;
import br.com.zup.proposta.integracao.ExecutorTransacao;
import br.com.zup.proposta.analiseproposta.StatusAvaliacaoProposta;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(PropostaController.class);

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
        try {
            executorTransacao.salvaEComita(novaProposta);
            StatusAvaliacaoProposta avaliacao = avaliaProposta.executa(novaProposta);
            novaProposta.atualizaStatus(avaliacao);
            executorTransacao.atualizaEComita(novaProposta);
            URI enderecoConsulta = uriComponentsBuilder.path("/propostas/{id}").build(novaProposta.getId());
            logger.info("Proposta de Documento = {} e Status = {} criada com sucesso!",
                    novaProposta.getDocumento(), novaProposta.getStatusAvaliacao());
            return ResponseEntity.created(enderecoConsulta).build();
        } catch (FeignException.UnprocessableEntity e) {
            executorTransacao.salvaEComita(novaProposta);
            URI enderecoConsulta = uriComponentsBuilder.path("/propostas/{id}").build(novaProposta.getId());
            logger.info("Proposta de Documento = {} e Status = {} criada com sucesso!",
                    novaProposta.getDocumento(), novaProposta.getStatusAvaliacao());
            return ResponseEntity.created(enderecoConsulta).build();
        }
    }
}
