package com.github.marcoscoutozup.proposta.proposta;

import com.github.marcoscoutozup.proposta.exception.StandardError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(ConsultaPropostaController.class);

    public ConsultaPropostaController(PropostaRepository propostaRepository) {
        this.propostaRepository = propostaRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity consultarPropostaPorId(@PathVariable UUID id){
        Optional<Proposta> proposta = propostaRepository.findById(id);

        //2
        if(proposta.isEmpty()){
            logger.warn("[CONSULTA DE PROPOSTA] Proposta não encontrada, id consultado: " + id);
            //3
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StandardError(Arrays.asList("Não existe proposta cadastrada")));
        }

        logger.warn("[CONSULTA DE PROPOSTA] Proposta encontrada, id consultado: " + id);
        //4
        PropostaResponse response = new PropostaResponse(proposta.get());
        return ResponseEntity.ok(response);
    }
}
