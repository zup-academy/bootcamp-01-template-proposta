package br.com.zup.bootcamp.proposta.api.controller;

import br.com.zup.bootcamp.proposta.domain.repository.CartaoRepository;
import br.com.zup.bootcamp.proposta.domain.service.BloqueiaCartaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

@RestController
public class BloquearCartaoController {

    private final CartaoRepository cartaoRepository;

    private final BloqueiaCartaoService bloqueiaService;

    private final Logger logger = LoggerFactory.getLogger(BloquearCartaoController.class);


    public BloquearCartaoController(CartaoRepository cartaoRepository, BloqueiaCartaoService bloqueiaService) {
        this.cartaoRepository = cartaoRepository;
        this.bloqueiaService = bloqueiaService;
    }

    @PostMapping("cartoes/{idCartao}/bloqueios")
    public ResponseEntity<?> boquearCartao(@PathVariable String idCartao, HttpServletRequest httpRequest, UriComponentsBuilder uri) {
        var cartao = cartaoRepository.findById(idCartao);
        if (cartao.isEmpty()) {
            logger.warn("Cartão com o id: {} não encontrado", idCartao);
            return ResponseEntity.notFound().build();
        }
        return bloqueiaService.processaBloqueio(httpRequest, cartao.get(), uri);
    }
}
