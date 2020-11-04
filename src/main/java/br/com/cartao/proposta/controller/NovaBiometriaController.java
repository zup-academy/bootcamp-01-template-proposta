package br.com.cartao.proposta.controller;

import br.com.cartao.proposta.domain.model.Biometria;
import br.com.cartao.proposta.domain.model.Proposta;
import br.com.cartao.proposta.domain.request.NovaBiometriaRequest;
import br.com.cartao.proposta.domain.response.NovaBiometriaResponseDto;
import br.com.cartao.proposta.repository.BiometriaRepository;
import br.com.cartao.proposta.repository.PropostaRepository;
import br.com.cartao.proposta.validator.Base64Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Carga intrínseca máxima permitida - 7
 * Carga intrínseca da classe - 8
 */

@RestController
@RequestMapping("/v1/biometrias")
public class NovaBiometriaController {

    private static Logger logger = LoggerFactory.getLogger(NovaBiometriaController.class);
    // +1
    private final PropostaRepository propostaRepository;
    // +1
    private final BiometriaRepository biometriaRepository;

    public NovaBiometriaController(PropostaRepository propostaRepository, BiometriaRepository biometriaRepository) {
        this.propostaRepository = propostaRepository;
        this.biometriaRepository = biometriaRepository;
    }

    @InitBinder
    // +1
    protected void init(WebDataBinder binder){
        binder.addValidators(new Base64Validator());
    }

    @PostMapping("/{id}")
    // +1
    public ResponseEntity<?> criaBiometria(@PathVariable("id") String idCartao,
                                           @RequestBody @Valid NovaBiometriaRequest novaBiometriaRequest,
                                           UriComponentsBuilder uriComponentsBuilder){
        logger.info("Requisição para nova biometria recebida: {}", novaBiometriaRequest);
        // +1
        Optional<Proposta> propostaBuscadaPeloCartao = propostaRepository.findByCartaoId(idCartao);
        // +1
        if (propostaBuscadaPeloCartao.isEmpty()){
            logger.info("ipProposta não encontrado: {}", idCartao);
            return ResponseEntity.notFound().build();
        }
        // +1
        Biometria biometria = novaBiometriaRequest.toModel(idCartao);

        biometriaRepository.save(biometria);
        // +1
        NovaBiometriaResponseDto novabiometriaResponseDto = new NovaBiometriaResponseDto(biometria);

        return ResponseEntity
                .created(uriComponentsBuilder.path("/v1/biometrias/{}").buildAndExpand(novabiometriaResponseDto.getId()).toUri())
                .build();
    }
}
