package com.github.marcoscoutozup.proposta.proposta;

import com.github.marcoscoutozup.proposta.exception.StandardError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/proposta")
public class ConsultaPropostaController {

                //1
    private PropostaRepository propostaRepository;

    public ConsultaPropostaController(PropostaRepository propostaRepository) {
        this.propostaRepository = propostaRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity consultarPropostaPorId(@PathVariable UUID id){
        Optional<Proposta> proposta = propostaRepository.findById(id);

        //2
        if(proposta.isEmpty()){                                                //3
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StandardError(Arrays.asList("Não existe proposta cadastrada")));
        }
        //4
        PropostaResponse response = new PropostaResponse(proposta.get());
        return ResponseEntity.ok(response);
    }
}
