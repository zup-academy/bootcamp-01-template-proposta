package br.com.zup.bootcamp.proposta.api.controller;

import br.com.zup.bootcamp.proposta.api.dto.ResponsePropostaDto;
import br.com.zup.bootcamp.proposta.domain.repository.PropostaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class ConsultaPropostaController {

    @Autowired      //1
    private PropostaRepository repository;
    private final Logger logger = LoggerFactory.getLogger(ConsultaPropostaController.class);

    @GetMapping(value="/propostas/{id}")
    public ResponseEntity<?> consultarProposta(@PathVariable String id){
        logger.info("Realizar consulta da proposta com id {} ", id);
        var proposta = repository.findById(id);
        //1
        if (proposta.isEmpty()){
            logger.warn("Proposta com id {} não encontrada ", id);
            return ResponseEntity.notFound().build();
        }         //1
        logger.info("Consulta da proposta {} realizada com sucesso", proposta.get().getId());
        return ResponseEntity.ok(ResponsePropostaDto.toDto(proposta.get()));
    }
    
}
