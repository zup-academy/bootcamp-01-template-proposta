package br.com.proposta.resources;

import br.com.proposta.dtos.requests.BiometriaRequest;
import br.com.proposta.entidades.Biometria;
import br.com.proposta.entidades.Cartao;
import br.com.proposta.repositories.BiometriaRepository;
import br.com.proposta.repositories.CartaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;


@RestController
@RequestMapping("/api/biometrias/{numeroCartao}")
public class BiometriaResource {

    /* total de pontos = 8 */

    /* @complexidade - acoplamento contextual */
    private final CartaoRepository cartaoRepository;

    /* @complexidade - acoplamento contextual */
    private final BiometriaRepository biometriaRepository;


    private final EntityManager entityManager;

    private final Logger logger = LoggerFactory.getLogger(Biometria.class);


    public BiometriaResource(CartaoRepository cartaoRepository, BiometriaRepository biometriaRepository, EntityManager entityManager) {
        this.cartaoRepository = cartaoRepository;
        this.biometriaRepository = biometriaRepository;
        this.entityManager = entityManager;
    }


    @Transactional
    @PostMapping                                                          /* @complexidade - classe criada no projeto */
    public ResponseEntity<?> criaBiometria(@PathVariable String numeroCartao, @RequestBody @Valid BiometriaRequest biometriaRequest,
                                           UriComponentsBuilder uriComponentsBuilder){


        /* @complexidade (2) - classe criada no projeto + if */
        var cartao = cartaoRepository.findByNumero(numeroCartao);
        if(cartao.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        /* @complexidade (3) - classe criada no projeto */
        var biometria = biometriaRequest.toModel();
        biometria.associaCartao(cartao.get());
        biometriaRepository.save(biometria);

        /* @complexidade (1) - classe criada no projeto */
        cartao.get().adicionarBiometria(biometria);
        entityManager.merge(cartao.get());

        logger.info("Biometria registrada com sucesso." +
                " Pode ser identificada pelo número {}", biometria.getId());

        return ResponseEntity
                .created(uriComponentsBuilder.path("/api/biometrias/{numeroCartao}").buildAndExpand(numeroCartao).toUri()).build();

    }
}
