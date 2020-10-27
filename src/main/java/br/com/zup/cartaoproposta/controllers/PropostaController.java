package br.com.zup.cartaoproposta.controllers;

import br.com.zup.cartaoproposta.entities.proposta.PropostaNovoRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Contagem de carga intrínseca da classe:
 */

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    @PostMapping
    public ResponseEntity<PropostaNovoRequest> criaProposta(@RequestBody @Valid PropostaNovoRequest novaProposta) {
        return ResponseEntity.ok(novaProposta);
    }
}
