package br.com.zup.proposta.proposta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class AcompanhaPropostaController {
    @Autowired
    PropostaRepository propostaRepository;

    @GetMapping("/propostas/{id}")
    public AcompanhaPropostaResponse acompanha(@PathVariable String id) {
        Proposta proposta = propostaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return new AcompanhaPropostaResponse(proposta);
    }
}
