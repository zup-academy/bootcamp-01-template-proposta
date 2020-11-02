package br.com.cartao.proposta.controller;

import br.com.cartao.proposta.domain.model.Proposta;
import br.com.cartao.proposta.domain.request.NovaPropostaRequest;
import br.com.cartao.proposta.domain.response.NovaPropostaResponseDto;
import br.com.cartao.proposta.handler.ErroNegocioException;
import br.com.cartao.proposta.repository.PropostaRepository;
import br.com.cartao.proposta.service.NovaPropostaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    private final NovaPropostaService novaPropostaService;

    public NovaPropostaController(PropostaRepository propostaRepository, NovaPropostaService novaPropostaService) {
        this.propostaRepository = propostaRepository;
        this.novaPropostaService = novaPropostaService;
    }

    @PostMapping
    @Transactional
    // +1
    public ResponseEntity<?> criaNovaProposta(@RequestBody @Valid NovaPropostaRequest novaPropostaRequest, UriComponentsBuilder uriComponentsBuilder) throws JsonProcessingException {
        // +1
        Optional<Proposta> propostaBuscadaPeloDocumento = propostaRepository.findByDocumento(novaPropostaRequest.getDocumento());
        // +1
        if (propostaBuscadaPeloDocumento.isPresent()) {
            throw new ErroNegocioException(HttpStatus.UNPROCESSABLE_ENTITY, "CPF ou CNPJ já em uso");
        }

        logger.info("Requisição recebida: {}", novaPropostaRequest);

        // +1
        NovaPropostaResponseDto novaPropostaResponseDto = novaPropostaService.criaNovaProposta(novaPropostaRequest);

        return ResponseEntity
                .created(uriComponentsBuilder.path("/v1/propostas/{id}").buildAndExpand(novaPropostaResponseDto.getId()).toUri())
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> verificaStatusProposta(@PathVariable String id){
        logger.info("Requisição recebida para verificar o status da proposta. idProposta: {}", id);
        Optional<Proposta> propostaBuscada  = propostaRepository.findById(id);
        // +1
        if (propostaBuscada.isEmpty()){
            logger.warn("Id proposta não encontrado. idProposta: {}", id);
            return ResponseEntity.notFound().build();
        }

        NovaPropostaResponseDto novaPropostaResponseDto = new NovaPropostaResponseDto(propostaBuscada.get());

        return ResponseEntity.ok(novaPropostaResponseDto);
    }
}
