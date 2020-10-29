package dev.arielalvesdutra.proposta.controllers;

import dev.arielalvesdutra.proposta.controllers.dtos.CadastrarBiometriaRequestDTO;
import dev.arielalvesdutra.proposta.entities.Biometria;
import dev.arielalvesdutra.proposta.services.BiometriaService;
import dev.arielalvesdutra.proposta.services.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RequestMapping("/api/cartoes")
@RestController
public class CartaoController {

    @Autowired
    public CartaoService cartaoService;

    @Autowired
    private BiometriaService biometriaService;

    public CartaoController(
            CartaoService cartaoService,
            BiometriaService biometriaService) {

        this.cartaoService = cartaoService;
        this.biometriaService = biometriaService;
    }

    @PostMapping("/{cartaoId}/biometrias")
    public ResponseEntity<String> cadastrarBiometria(
            UriComponentsBuilder uriBuilder,
            @PathVariable String cartaoId,
            @Valid @RequestBody CadastrarBiometriaRequestDTO requestDTO){

        final Biometria biometriaCadastrada = biometriaService.cadastrar(cartaoId, requestDTO);

        final URI uri = uriBuilder.path("/api/cartoes/{cartaoId}/biometrias/{biometriaId}")
                .buildAndExpand(cartaoId, biometriaCadastrada.getId())
                .toUri();

        return ResponseEntity.created(uri).body(biometriaCadastrada.getId());
    }
}
