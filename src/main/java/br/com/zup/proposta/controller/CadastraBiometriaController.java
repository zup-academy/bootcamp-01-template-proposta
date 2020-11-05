package br.com.zup.proposta.controller;

import br.com.zup.proposta.dto.request.BiometriaRequest;
import br.com.zup.proposta.model.Biometria;
import br.com.zup.proposta.model.Cartao;
import br.com.zup.proposta.utils.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/cartoes")
public class CadastraBiometriaController {

    private EntityManager entityManager;

    public CadastraBiometriaController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping("/{cartaoID}/biometria")
    @Transactional
    public ResponseEntity cadastroBiometria(@PathVariable UUID cartaoID, @RequestBody @Valid BiometriaRequest request, UriComponentsBuilder builder) {

        Optional<Cartao> cartaoProcurado = Optional.ofNullable(entityManager.find(Cartao.class, cartaoID));

        if (cartaoProcurado.isPresent()) {
            Biometria biometria = request.toModel();
            entityManager.persist(biometria);

            Cartao cartao = cartaoProcurado.get();
            cartao.incluirBiometriaNoCartao(biometria);
            entityManager.merge(cartao);

            URI enderecoConsulta = builder.path("/biometrias/{id}").buildAndExpand(cartaoID).toUri();
            return ResponseEntity.created(enderecoConsulta).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error(Arrays.asList("Cartão não encontrado")));
        }

    }
}
