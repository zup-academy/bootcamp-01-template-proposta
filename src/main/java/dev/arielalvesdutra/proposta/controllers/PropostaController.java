package dev.arielalvesdutra.proposta.controllers;

import dev.arielalvesdutra.proposta.controllers.dtos.CadastrarPropostaDTO;
import dev.arielalvesdutra.proposta.controllers.dtos.PropostaResponseDTO;
import dev.arielalvesdutra.proposta.entities.Proposta;
import dev.arielalvesdutra.proposta.services.PropostaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RequestMapping("/api/propostas")
@RestController
public class PropostaController {

    @Autowired
    private PropostaService propostaService;

    public PropostaController(PropostaService propostaService) {
        this.propostaService = propostaService;
    }

    @PostMapping
    public ResponseEntity<String> cadastrar(
            UriComponentsBuilder uriBuilder,
            @Valid @RequestBody CadastrarPropostaDTO request) {

        final var propostaCadastrada = propostaService.cadastrar(request);

        final URI uri = uriBuilder.path("/api/propostas/{id}")
                .buildAndExpand(propostaCadastrada.getId())
                .toUri();

        return ResponseEntity.created(uri).body(propostaCadastrada.getId());
    }

    @GetMapping("/{propostaId}")
    public ResponseEntity<PropostaResponseDTO> buscaPeloId(@PathVariable String propostaId) {
        final Proposta proposta = propostaService.buscaPeloId(propostaId);

        return ResponseEntity.ok().body(new PropostaResponseDTO(proposta));
    }
}
