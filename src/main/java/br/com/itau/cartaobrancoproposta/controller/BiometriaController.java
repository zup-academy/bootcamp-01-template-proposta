package br.com.itau.cartaobrancoproposta.controller;

import br.com.itau.cartaobrancoproposta.config.MetricasConfig;
import br.com.itau.cartaobrancoproposta.model.Biometria;
import br.com.itau.cartaobrancoproposta.model.BiometriaRequest;
import br.com.itau.cartaobrancoproposta.model.Cartao;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.net.URI;

@RestController
public class BiometriaController {

    private final Tracer tracer;

    Logger logger = LoggerFactory.getLogger(BiometriaController.class);

    private final EntityManager entityManager;
//1
    private final MetricasConfig metricasConfig;

    public BiometriaController(Tracer tracer, EntityManager entityManager, MetricasConfig metricasConfig) {
        this.tracer = tracer;
        this.entityManager = entityManager;
        this.metricasConfig = metricasConfig;
    }

    @PostMapping("/v1/cartoes/{id}/biometrias")
    @Transactional
    public ResponseEntity<?> criaBiometria(@Valid @NotBlank @PathVariable(name = "id") String id,
                                           @Valid @RequestBody BiometriaRequest biometriaRequest, UriComponentsBuilder builder) { //1
        Query query = entityManager.createQuery("SELECT u FROM Cartao u WHERE u.numeroCartao =: value");
        query.setParameter("value", id);

        if (query.getResultList().isEmpty()) { //1
            logger.error("Cartão com final {} não foi encontrado.", id.substring(24));
            return ResponseEntity.notFound().build();
        }

        Cartao cartao = (Cartao) query.getResultList().get(0); //1
        logger.info("Cartão com o id={} foi encontrado.", cartao.getId());

        Span activeSpan = tracer.activeSpan();
        activeSpan.setTag("correlationID", cartao.getId());

        Biometria biometria = biometriaRequest.toModel(); //1

        entityManager.persist(biometria);
        logger.info("Biometria id={} foi criada com sucesso!", biometria.getId());

        cartao.carregaBiometria(biometria);
        entityManager.merge(cartao);
        logger.info("Biometria id={} foi atrelado ao cartão com final {} com sucesso!", biometria.getId(), cartao.getNumeroCartao().substring(24));

        URI endereco = builder.path("/v1/biometrias/{id}").buildAndExpand(biometria.getId()).toUri();

        metricasConfig.incrementaContador("biometria_cadastrada");

        return ResponseEntity.created(endereco).build();
    }
}
