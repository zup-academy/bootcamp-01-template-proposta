package br.com.itau.cartaobrancoproposta.controller;

import br.com.itau.cartaobrancoproposta.model.Biometria;
import br.com.itau.cartaobrancoproposta.model.BiometriaRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class BiometriaController {

    @PostMapping("/v1/biometria")
    public Biometria criaBiometria(@Valid @RequestBody BiometriaRequest biometriaRequest) {
        Biometria biometria = biometriaRequest.toModel();
        return biometria;
    }
}
