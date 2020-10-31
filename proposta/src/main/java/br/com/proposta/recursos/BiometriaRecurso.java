package br.com.proposta.recursos;

import br.com.proposta.transferenciadados.requisicoes.RequisicaoBiometria;
import br.com.proposta.entidades.Biometria;
import br.com.proposta.repositorios.BiometriaRepository;
import br.com.proposta.repositorios.PropostaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/biometrias/{cartaoId}")
public class BiometriaRecurso {

    /* total de pontos = 4 */

    private final Logger logger = LoggerFactory.getLogger(Biometria.class);

    /* @complexidade - classe criada no projeto */
    private final PropostaRepository propostaRepository;

    /* @complexidade - classe criada no projeto */
    private final BiometriaRepository biometriaRepository;


    public BiometriaRecurso(PropostaRepository propostaRepository, BiometriaRepository biometriaRepository) {
        this.propostaRepository = propostaRepository;
        this.biometriaRepository = biometriaRepository;
    }


    @PostMapping                                                          /* @complexidade - classe criada no projeto */
    public ResponseEntity<?> criaBiometria(@PathVariable String cartaoId, @RequestBody @Valid RequisicaoBiometria requisicaoBiometria,
                                           UriComponentsBuilder uriComponentsBuilder){



        /* @complexidade - classe criada no projeto */
        var biometria = requisicaoBiometria.toModel();

        biometriaRepository.save(biometria);

        logger.info("Biometria registrada com sucesso");

        return ResponseEntity
                .created(uriComponentsBuilder.path("/biometrias/{cartaoId}").buildAndExpand(biometria.getId()).toUri()).build();

    }
}
