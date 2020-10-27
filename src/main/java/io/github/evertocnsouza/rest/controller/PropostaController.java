package io.github.evertocnsouza.rest.controller;

import io.github.evertocnsouza.domain.component.ExecutorTransacao;
import io.github.evertocnsouza.domain.entity.Proposta;
import io.github.evertocnsouza.domain.enums.StatusAvaliacaoProposta;
import io.github.evertocnsouza.domain.service.AvaliaProposta;
import io.github.evertocnsouza.rest.dto.PropostaRequest;
import io.github.evertocnsouza.validation.BloqueiaDocIgualValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;



@RestController
@RequestMapping("propostas")
public class PropostaController {

    @Autowired
    private BloqueiaDocIgualValidator bloqueiaDocIgualValidator;

    @Autowired
    private ExecutorTransacao executorTransacao;

    @Autowired
    private AvaliaProposta avaliaProposta;


    public PropostaController(BloqueiaDocIgualValidator bloqueiaDocIgualValidator,
                              AvaliaProposta avaliaProposta, ExecutorTransacao executorTransacao) {
        super();
        this.bloqueiaDocIgualValidator = bloqueiaDocIgualValidator;
        this.avaliaProposta = avaliaProposta;
        this.executorTransacao = executorTransacao;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> save(@RequestBody @Valid PropostaRequest request,
                                  UriComponentsBuilder uriComponentsBuilder) {
        if(!bloqueiaDocIgualValidator.estaValido(request)){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Proposta proposta = request.ToModel();
        executorTransacao.salvaEComita(proposta);

        StatusAvaliacaoProposta avaliacao = avaliaProposta.executa(proposta);
        proposta.atualizaStatus(avaliacao);

        executorTransacao.atualizaEComita(proposta);

        URI enderecoConsulta = uriComponentsBuilder.path("{id}").build(proposta.getId());
        return ResponseEntity.created(enderecoConsulta).build();
    }

}
