package br.com.proposta.resources;

import br.com.proposta.dtos.requests.BiometriaRequest;
import br.com.proposta.entidades.Biometria;
import br.com.proposta.repositories.BiometriaRepository;
import br.com.proposta.repositories.PropostaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/biometrias/{cartaoId}")
public class BiometriaResource {

    /* total de pontos = 4 */

    private final Logger logger = LoggerFactory.getLogger(Biometria.class);

    /* @complexidade - acoplamento contextual */
    private final PropostaRepository propostaRepository;

    /* @complexidade - acoplamento contextual */
    private final BiometriaRepository biometriaRepository;


    public BiometriaResource(PropostaRepository propostaRepository, BiometriaRepository biometriaRepository) {
        this.propostaRepository = propostaRepository;
        this.biometriaRepository = biometriaRepository;
    }


    @PostMapping                                                          /* @complexidade - classe criada no projeto */
    public ResponseEntity<?> criaBiometria(@PathVariable String cartaoId, @RequestBody @Valid BiometriaRequest biometriaRequest,
                                           UriComponentsBuilder uriComponentsBuilder){



        /* @complexidade - classe criada no projeto */
        var biometria = biometriaRequest.toModel();

        biometriaRepository.save(biometria);

        logger.info("Biometria registrada com sucesso");

        return ResponseEntity
                .created(uriComponentsBuilder.path("/biometrias/{cartaoId}").buildAndExpand(biometria.getId()).toUri()).build();

    }
}
