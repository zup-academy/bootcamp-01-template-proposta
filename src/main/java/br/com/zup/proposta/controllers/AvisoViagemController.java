package br.com.zup.proposta.controllers;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.proposta.controllers.form.AvisoViagemForm;
import br.com.zup.proposta.model.cartao.Cartao;
import br.com.zup.proposta.service.CartaoUtilsService;

@RestController
public class AvisoViagemController {

    @Autowired
    private CartaoUtilsService service;

    @PostMapping("/api/cartao/aviso/{id}")
    public ResponseEntity<?> cadastroAvisoViagem(@PathVariable String id, @RequestBody AvisoViagemForm form,
            HttpServletRequest request, UriComponentsBuilder uriBuilder) {
        Cartao cartao = service.cadastraAvisoDeViagem(id, form, request);

        URI uri = uriBuilder.path("api/cartao/{id}").buildAndExpand(cartao.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
