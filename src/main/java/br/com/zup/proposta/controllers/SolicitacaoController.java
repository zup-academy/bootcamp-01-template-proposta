package br.com.zup.proposta.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.proposta.controllers.dto.DetailedPropostaDto;
import br.com.zup.proposta.controllers.dto.PropostaDto;
import br.com.zup.proposta.controllers.form.SolicitacaoForm;
import br.com.zup.proposta.service.PropostaService;
import br.com.zup.proposta.service.validadores.PropostaDuplicadaValidador;

@RestController
public class SolicitacaoController {
    
    @Autowired
    private PropostaService service;
    @Autowired
    private PropostaDuplicadaValidador propostaDuplicadaValidador;

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(propostaDuplicadaValidador);
    }

    @PostMapping("/propostas")
    public ResponseEntity<PropostaDto> criarSolicitacao(@RequestBody @Valid SolicitacaoForm form, UriComponentsBuilder builder) {
        final PropostaDto propostaCriada = service.criar(form.toProposta());

        final URI uri = builder.path("/propostas/{id}").buildAndExpand(propostaCriada.getId()).toUri();
        return ResponseEntity.created(uri).body(propostaCriada);
    }

    @GetMapping("/propostas/{id}")
    public ResponseEntity<DetailedPropostaDto> buscarProposta(@PathVariable String id) {
        return ResponseEntity.ok(service.buscarPorId(id).toDetailedDto());
    }
}
