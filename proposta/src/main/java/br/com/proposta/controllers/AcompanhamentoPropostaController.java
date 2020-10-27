package br.com.proposta.controllers;

import br.com.proposta.dtos.responses.PropostaResponse;
import br.com.proposta.models.Proposta;
import br.com.proposta.repositories.PropostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/propostas/{id}")
public class AcompanhamentoPropostaController {

    @Autowired
    private PropostaRepository propostaRepository;

    @GetMapping
    public ResponseEntity<?> acompanharProposta(@PathVariable Long id){

        Proposta propostaAcompanhamento =
                propostaRepository.findById(id).get();

        return ResponseEntity.ok(new PropostaResponse(propostaAcompanhamento));

    }

}
