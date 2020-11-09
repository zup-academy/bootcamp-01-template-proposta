package br.com.zup.proposta.controllers;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.proposta.model.cartao.Cartao;
import br.com.zup.proposta.service.CartaoService;

@RestController
public class CartaoBloqueioController {
    
    @Autowired
    private CartaoService service;

    @RequestMapping(method = RequestMethod.POST, path = "/api/cartao/bloqueio/{id}")
    public ResponseEntity<?> solicitaBloqueio(@PathVariable String id, HttpServletRequest request, 
            UriComponentsBuilder uriBuilder) {

        final Cartao cartao = service.solicitaBloqueio(id, request);

        final URI uri = uriBuilder.path("/api/cartao/{id}").buildAndExpand(cartao.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
