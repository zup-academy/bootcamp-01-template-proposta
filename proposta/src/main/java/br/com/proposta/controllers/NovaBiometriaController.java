package br.com.proposta.controllers;

import br.com.proposta.dtos.requests.BiometriaRequest;
import br.com.proposta.models.Biometria;
import br.com.proposta.repositories.BiometriaRepository;
import br.com.proposta.repositories.PropostaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;


@RestController
@RequestMapping("/biometrias/{cartaoId}")
public class NovaBiometriaController {


    private final Logger logger = LoggerFactory.getLogger(Biometria.class);

    private PropostaRepository propostaRepository;

    private BiometriaRepository biometriaRepository;


    public NovaBiometriaController(PropostaRepository propostaRepository, BiometriaRepository biometriaRepository) {
        this.propostaRepository = propostaRepository;
        this.biometriaRepository = biometriaRepository;
    }


    @PostMapping
    public ResponseEntity<?> criaBiometria(@PathVariable String cartaoId,
                                           @RequestBody @Valid BiometriaRequest biometriaRequest, UriComponentsBuilder uriComponentsBuilder){


        Biometria biometria = biometriaRequest.toModel();

        biometriaRepository.save(biometria);


        logger.info("Biometria registrada com sucesso");

        return ResponseEntity
                .created(uriComponentsBuilder.path("/biometrias/{cartaoId}").buildAndExpand(biometria.getId()).toUri()).build();

    }
}
