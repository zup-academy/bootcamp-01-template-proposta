package br.com.zup.proposta.integracao.cartao.biometria;

import br.com.zup.proposta.integracao.ExecutorTransacao;
import br.com.zup.proposta.integracao.cartao.Cartao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.validation.Valid;

@RestController
@RequestMapping("/cartoes/biometrias")
public class BiometriaController {
    @Autowired
    private EntityManager manager;

    private Logger logger = LoggerFactory.getLogger(BiometriaController.class);

    @PostMapping("/{idCartao}")
    @Transactional
    public ResponseEntity<?> cadastrarBiometria(@PathVariable String idCartao,
                                               @RequestBody @Valid BiometriaRequest request,
                                               UriComponentsBuilder builder) {
        Cartao cartao = manager.find(Cartao.class, idCartao);

        if (cartao == null) {
            logger.info("Cartão não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Biometria biometria = request.toBiometria();
        manager.persist(biometria);
        logger.info("Biometria cadastrada");

        cartao.addBiometria(biometria);
        manager.merge(cartao);
        logger.info("Biometria associada ao cartão = {}", cartao.getNumeroCartao());

        return ResponseEntity.created(builder.path("/biometrias/{id}")
                .buildAndExpand(idCartao).toUri()).build();
    }
}
