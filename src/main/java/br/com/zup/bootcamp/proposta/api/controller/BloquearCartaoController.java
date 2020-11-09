package br.com.zup.bootcamp.proposta.api.controller;

import br.com.zup.bootcamp.proposta.domain.repository.CartaoRepository;
import br.com.zup.bootcamp.proposta.domain.service.BloqueiaCartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

@RestController
public class BloquearCartaoController {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private BloqueiaCartaoService bloqueiaService;

    @PostMapping("cartoes/{idCartao}/bloqueios")
    public ResponseEntity<?> boquearCartao(@PathVariable String idCartao, HttpServletRequest httpRequest, UriComponentsBuilder uri) {
        var cartao = cartaoRepository.findById(idCartao);
        if (cartao.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return bloqueiaService.processaBloqueio(httpRequest, cartao.get(), uri);
    }
}
