package br.com.zup.proposta.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.proposta.model.cartao.Cartao;
import br.com.zup.proposta.service.CartaoService;

@RestController
public class BiometriaController {
    
    @Autowired
    private CartaoService service;

    @PostMapping("/cartao/biometria/{id}")
    public ResponseEntity<?> cadastrarBiometria(@PathVariable String id, @RequestBody MultipartFile file, 
        UriComponentsBuilder uriBuilder) {
        Cartao cartaoAtualizado = service.cadastrarBiometria(id, file);

        URI uri = uriBuilder.path("/cartao/{id}").buildAndExpand(cartaoAtualizado.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
