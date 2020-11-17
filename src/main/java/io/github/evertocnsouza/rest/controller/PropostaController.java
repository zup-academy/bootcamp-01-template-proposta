package io.github.evertocnsouza.rest.controller;

import feign.FeignException;
import io.github.evertocnsouza.domain.component.ExecutorTransacao;
import io.github.evertocnsouza.domain.entity.Proposta;
import io.github.evertocnsouza.domain.enums.StatusAvaliacaoProposta;
import io.github.evertocnsouza.domain.service.PropostaAvaliacao;
import io.github.evertocnsouza.rest.dto.request.PropostaRequest;
import io.github.evertocnsouza.validation.BloqueiaDocIgualValidator;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@RequestMapping("/propostas")
@RestController
public class PropostaController {
//8 PCI's. O try catch me quebrou! rs
    @Autowired
    private BloqueiaDocIgualValidator bloqueiaDocumentoIgualValidator;
    //PCI 1

    @Autowired
    private ExecutorTransacao executorTransacao;
    //PCI 2

    @Autowired
    private PropostaAvaliacao propostaAvaliacao;
    //PCI 3

    @Autowired
    private Tracer tracer;

    private Logger logger = LoggerFactory.getLogger(Proposta.class);


    @Transactional
    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid PropostaRequest request, //PCI 4
                                  UriComponentsBuilder builder) {

        Span activeSpan = tracer.activeSpan();
        activeSpan.setTag("user.email", "everton@gmail.com");

        //PCI 5
        if (!bloqueiaDocumentoIgualValidator.estaValido(request)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Proposta proposta = request.toModel();
        //PCI 6 e 7
        try {
            executorTransacao.salvaEComita(proposta);
            StatusAvaliacaoProposta avaliacao = propostaAvaliacao.executa(proposta);
            proposta.atualizaStatus(avaliacao);
            executorTransacao.atualizaEComita(proposta);

            logger.info("A proposta de número={}, foi criada com sucesso",
                    proposta.getId());

            URI enderecoConsulta = builder.path("/propostas/{id}").build(proposta.getId());
            return ResponseEntity.created(enderecoConsulta).build();

        //PCI 8
        } catch (FeignException.UnprocessableEntity e) {}
               executorTransacao.salvaEComita(proposta);

            logger.info("A proposta de número={}, foi criada com sucesso",
                    proposta.getId());

            URI enderecoConsulta = builder.path("/propostas/{id}").build(proposta.getId());
            return ResponseEntity.created(enderecoConsulta).build();
        }


}
