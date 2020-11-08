package br.com.zup.proposta.controller;

import br.com.zup.proposta.dao.ExecutorTransacao;
import br.com.zup.proposta.metrics.MinhasMetricas;
import br.com.zup.proposta.services.AvaliaProposta;
import br.com.zup.proposta.dto.NovaPropostaRequest;
import br.com.zup.proposta.model.Proposta;
import br.com.zup.proposta.model.enums.StatusAvaliacaoProposta;
import br.com.zup.proposta.validations.DocumentoIgualValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/propostas")
public class NovaPropostaController {

    private DocumentoIgualValidator documentoIgualValidator; //1
    private AvaliaProposta avaliaProposta; //2
    private ExecutorTransacao executorTransacao; //3

    private MinhasMetricas minhasMetricas;

    private final Logger logger = LoggerFactory.getLogger(NovaPropostaController.class);

    public NovaPropostaController(DocumentoIgualValidator documentoIgualValidator,
                                  AvaliaProposta avaliaProposta, ExecutorTransacao executorTransacao,
                                  MinhasMetricas minhasMetricas) {
        this.documentoIgualValidator = documentoIgualValidator;
        this.avaliaProposta = avaliaProposta;
        this.executorTransacao = executorTransacao;
        this.minhasMetricas = minhasMetricas;
    }

    @PostMapping
    @Transactional
    public ResponseEntity novaProposta(@RequestBody @Valid NovaPropostaRequest request,
                                       UriComponentsBuilder builder,
                                       @AuthenticationPrincipal Jwt jwt) { //4

        Optional<String> emailAutenticado = Optional.ofNullable(jwt.getClaim("email"));

        Assert.isTrue(emailAutenticado.isPresent(), "Para criar uma nova Proposta, " +
                "deve-se estar logado com um email autenticado");

        logger.info("Usuário autenticado (email): {}", emailAutenticado.get());

        if(documentoIgualValidator.existe(request)) //5
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body("Documento inválido!");

        Proposta novaProposta = request.toProposta(emailAutenticado.get()); //6

        logger.info("Proposta pendente: Nome={} , Documento={}, Status Avaliação={}",
                novaProposta.getNome() , novaProposta.getDocumento(),
                novaProposta.getStatusAvaliacaoProposta());

        executorTransacao.salvaEComita(novaProposta);

        StatusAvaliacaoProposta statusAvaliacaoProposta =
                avaliaProposta.executar(novaProposta); //7

        novaProposta.atualizaStatus(statusAvaliacaoProposta);

        logger.info("Proposta avaliada: Nome={} , Documento={}, Status Avaliação={}",
                novaProposta.getNome() , novaProposta.getDocumento(),
                novaProposta.getStatusAvaliacaoProposta());

        executorTransacao.atualizaEComita(novaProposta);

        minhasMetricas.meuContador();

        return ResponseEntity.created(builder.path("/api/propostas/{id}")
                .buildAndExpand(novaProposta.getId()).toUri()).build();
    }

}
