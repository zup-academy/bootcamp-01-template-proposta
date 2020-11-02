package br.com.proposta.resources;

import br.com.proposta.dtos.requests.BiometriaRequest;
import br.com.proposta.entidades.Biometria;
import br.com.proposta.entidades.Cartao;
import br.com.proposta.repositories.BiometriaRepository;
import br.com.proposta.repositories.CartaoRepository;
import br.com.proposta.repositories.PropostaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;


@RestController
@RequestMapping("/api/biometrias/{cartaoId}")
public class BiometriaResource {

    /* total de pontos = 6 */

    private final Logger logger = LoggerFactory.getLogger(Biometria.class);

    /* @complexidade - acoplamento contextual */
    private final CartaoRepository cartaoRepository;

    /* @complexidade - acoplamento contextual */
    private final BiometriaRepository biometriaRepository;

    private final EntityManager entityManager;


    public BiometriaResource(CartaoRepository cartaoRepository, BiometriaRepository biometriaRepository, EntityManager entityManager) {
        this.cartaoRepository = cartaoRepository;
        this.biometriaRepository = biometriaRepository;
        this.entityManager = entityManager;
    }


    @Transactional
    @PostMapping                                                          /* @complexidade - classe criada no projeto */
    public ResponseEntity<?> criaBiometria(@PathVariable String cartaoId, @RequestBody @Valid BiometriaRequest biometriaRequest,
                                           UriComponentsBuilder uriComponentsBuilder){


        /* @complexidade - classe criada no projeto */
        Cartao cartao = cartaoRepository.findByNumero(cartaoId);

        /* @complexidade - classe criada no projeto */
        var biometria = biometriaRequest.toModel();

        biometriaRepository.save(biometria);

        /* @complexidade - classe criada no projeto */
        cartao.adicionarBiometria(biometria);

        entityManager.merge(cartao);

        logger.info("Biometria registrada com sucesso." +
                " Pode ser identificada pelo número {}", biometria.getId());

        return ResponseEntity
                .created(uriComponentsBuilder.path("/api/biometrias/{cartaoId}").buildAndExpand(biometria.getId()).toUri()).build();

    }
}
