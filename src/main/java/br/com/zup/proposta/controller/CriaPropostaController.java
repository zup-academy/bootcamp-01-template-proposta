package br.com.zup.proposta.controller;

import br.com.zup.proposta.dto.request.NovaPropostaRequest;
import br.com.zup.proposta.enums.StatusAvaliacaoProposta;
import br.com.zup.proposta.integration.ExecutorTransacao;
import br.com.zup.proposta.model.Proposta;
import br.com.zup.proposta.service.AvaliaPropostaService;
import br.com.zup.proposta.validator.BloqueiaDocIgualValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.opentracing.Tracer;
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
@RequestMapping(value = "propostas")
public class CriaPropostaController {

    @Autowired
    private ExecutorTransacao executorTransacao;

    @Autowired
    private AvaliaPropostaService avaliaProposta;

    private Tracer tracer;

    @Autowired
    private BloqueiaDocIgualValidator bloqueiaDocIgualValidator;

    public CriaPropostaController(BloqueiaDocIgualValidator bloqueiaDocIgualValidator,
                                  ExecutorTransacao executorTransacao, AvaliaPropostaService avaliaProposta, Tracer tracer) {
        super();
        this.bloqueiaDocIgualValidator = bloqueiaDocIgualValidator;
        this.executorTransacao = executorTransacao;
        this.avaliaProposta = avaliaProposta;
        this.tracer = tracer;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> criaProposta (@RequestBody @Valid NovaPropostaRequest request, UriComponentsBuilder builder) throws JsonProcessingException {

        tracer.activeSpan().setTag("usuario.email", request.getEmail());
        tracer.activeSpan().setBaggageItem("usuario.email", request.getEmail());
        tracer.activeSpan().log("Cadastrando a proposta do usuário");

        if(!bloqueiaDocIgualValidator.validaDocumento(request)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "proposta já cadastrada para o cpf_cnpj");
        }

        Proposta novaProposta = request.toModel();
        executorTransacao.salva(novaProposta);

        StatusAvaliacaoProposta avaliacao = avaliaProposta.executa(novaProposta);
        novaProposta.atualizaStatus(avaliacao);

        executorTransacao.atualiza(novaProposta);

        URI enderecoConsulta = builder.path("/propostas/{id}").build(novaProposta.getId());
        return ResponseEntity.created(enderecoConsulta).build();
    }
}
