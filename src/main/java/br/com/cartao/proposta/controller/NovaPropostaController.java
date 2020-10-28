package br.com.cartao.proposta.controller;

import br.com.cartao.proposta.consumer.AnalisePropostaConsumer;
import br.com.cartao.proposta.domain.model.Proposta;
import br.com.cartao.proposta.domain.request.NovaPropostaRequest;
import br.com.cartao.proposta.domain.response.AnalisePropostResponse;
import br.com.cartao.proposta.domain.response.NovaPropostaResponseDto;
import br.com.cartao.proposta.handler.ErroNegocioException;
import br.com.cartao.proposta.repository.PropostaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

/**
 * Carga intrínseca máxima permitida - 7
 * Carga intrínseca da classe - 7
 */

@RestController
@RequestMapping("/v1/propostas")
public class NovaPropostaController {

    private static Logger logger = LoggerFactory.getLogger(NovaPropostaController.class);

    // +1
    private final PropostaRepository propostaRepository;
    // +1
    private final AnalisePropostaConsumer analiseConsumer;

    public NovaPropostaController(PropostaRepository propostaRepository, AnalisePropostaConsumer analiseConsumer) {
        this.propostaRepository = propostaRepository;
        this.analiseConsumer = analiseConsumer;
    }

    @PostMapping
    @Transactional
    // +1
    public ResponseEntity<?> criaNovaProposta(@RequestBody @Valid NovaPropostaRequest novaPropostaRequest, UriComponentsBuilder uriComponentsBuilder){

        Optional<Proposta> propostaBuscadaPeloDocumento = propostaRepository.findByDocumento(novaPropostaRequest.getDocumento());
        // +1
        if (propostaBuscadaPeloDocumento.isPresent()) {
            throw new ErroNegocioException(HttpStatus.UNPROCESSABLE_ENTITY, "CPF ou CNPJ já em uso");
        }

        logger.info("Requisição recebida: {}", novaPropostaRequest);
        // +1
        Proposta proposta = novaPropostaRequest.toModel();

        propostaRepository.save(proposta);
        // +1
        AnalisePropostResponse analisePropostResponse = analiseConsumer.avaliacaoFinanceira(proposta.toAnalisePropostaRequest());

        logger.info("Resposta da Analise da Proposta: {}",analisePropostResponse);
        proposta.adicionaEstadoProposta(analisePropostResponse);
        propostaRepository.save(proposta);
        // +1
        NovaPropostaResponseDto novaPropostaResponseDto = new NovaPropostaResponseDto(proposta);

        return ResponseEntity
                .created(uriComponentsBuilder.path("/v1/propostas/{id}").buildAndExpand(novaPropostaResponseDto.getId()).toUri())
                .build();
    }
}
