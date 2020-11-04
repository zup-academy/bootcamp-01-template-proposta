package br.com.cartao.proposta.controller;

import br.com.cartao.proposta.domain.model.Biometria;
import br.com.cartao.proposta.domain.model.Cartao;
import br.com.cartao.proposta.domain.request.NovaBiometriaRequest;
import br.com.cartao.proposta.domain.response.NovaBiometriaResponseDto;
import br.com.cartao.proposta.repository.CartaoRepository;
import br.com.cartao.proposta.validator.Base64Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

/**
 * Carga intrínseca máxima permitida - 7
 * Carga intrínseca da classe - 7
 */

@RestController
@RequestMapping("/v1/cartoes")
public class NovaBiometriaController {

    private static Logger logger = LoggerFactory.getLogger(NovaBiometriaController.class);
    @PersistenceContext
    private final EntityManager manager;
    // +1
    private final CartaoRepository cartaoRepository;

    public NovaBiometriaController(EntityManager manager, CartaoRepository cartaoRepository) {
        this.manager = manager;
        this.cartaoRepository = cartaoRepository;
    }

    @InitBinder
    // +1
    protected void init(WebDataBinder binder){
        binder.addValidators(new Base64Validator());
    }

    @PostMapping("/{id}/biometrias")
    @Transactional
    // +1
    public ResponseEntity<?> criaBiometria(@PathVariable("id") String cartaoId,
                                           @RequestBody @Valid NovaBiometriaRequest novaBiometriaRequest,
                                           UriComponentsBuilder uriComponentsBuilder){
        logger.info("Requisição para nova biometria recebida: {}", novaBiometriaRequest);
        // +1
        Optional<Cartao> cartaoBuscado = cartaoRepository.findByCartaoId(cartaoId);
        // +1
        if (cartaoBuscado.isEmpty()){
            logger.info("ipProposta não encontrado: {}", cartaoId);
            return ResponseEntity.notFound().build();
        }
        // +1
        Biometria biometria = novaBiometriaRequest.toModel(cartaoId);

        manager.persist(biometria);
        // +1
        NovaBiometriaResponseDto novabiometriaResponseDto = new NovaBiometriaResponseDto(biometria);

        return ResponseEntity
                .created(uriComponentsBuilder.path("/v1/biometrias/{}").buildAndExpand(novabiometriaResponseDto.getId()).toUri())
                .build();
    }
}
