package br.com.proposta.controllers;

import br.com.proposta.dtos.requests.BiometriaRequest;
import br.com.proposta.models.Biometria;
import br.com.proposta.models.Cartao;
import br.com.proposta.models.Proposta;
import br.com.proposta.repositories.CartaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/biometrias/{cartaoId}")
public class NovaBiometriaController {

    @Autowired
    private CartaoRepository cartaoRepository;

    private final Logger logger = LoggerFactory.getLogger(Biometria.class);

    @PostMapping
    public ResponseEntity<?> criaBiometria(@PathVariable String cartaoId,
                                           @RequestBody @Valid BiometriaRequest biometriaRequest, UriComponentsBuilder uriComponentsBuilder){

        Optional<Cartao> cartao = cartaoRepository.findById(cartaoId);

        if(!cartao.isPresent()){

            return ResponseEntity.notFound().build();

        }

        Biometria biometria = biometriaRequest.toModel();

        cartao.get().adicionaBiometria(biometria);

        logger.info("Biometria registrada com sucesso");

        return ResponseEntity
                .created(uriComponentsBuilder.path("/biometrias/{cartaoId}").buildAndExpand(cartao.get().getId()).toUri()).build();

    }
}
