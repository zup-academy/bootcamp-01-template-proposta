package br.com.proposta.controllers;

import br.com.proposta.dtos.responses.PropostaResponse;
import br.com.proposta.models.Proposta;
import br.com.proposta.repositories.PropostaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


    private PropostaRepository propostaRepository;

    private final Logger logger = LoggerFactory.getLogger(Proposta.class);


    public AcompanhamentoPropostaController(PropostaRepository propostaRepository) {
        this.propostaRepository = propostaRepository;
    }


    @GetMapping
    public ResponseEntity<?> acompanharProposta(@PathVariable String id){

        Proposta propostaAcompanhamento =
                propostaRepository.findById(id).get();

        logger.info("Acompanhamento da proposta do cliente={}",
                propostaAcompanhamento.getNome());

        return ResponseEntity.ok(new PropostaResponse(propostaAcompanhamento));

    }

}
