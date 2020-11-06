package com.github.marcoscoutozup.proposta.proposta;

import com.github.marcoscoutozup.proposta.exception.StandardError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/propostas")
public class ConsultarPropostaController {

                //1
    private PropostaRepository propostaRepository;

    private Logger logger = LoggerFactory.getLogger(ConsultarPropostaController.class);

    public ConsultarPropostaController(PropostaRepository propostaRepository) {
        this.propostaRepository = propostaRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity consultarPropostaPorId(@PathVariable UUID id,
                                                 @RequestHeader(name = "Authorization") String token){

                    //2
        Optional<Proposta> propostaProcurada = propostaRepository.findById(id);

        //3
        if(propostaProcurada.isEmpty()){
            logger.warn("[CONSULTA DE PROPOSTA] Proposta não encontrada, id consultado: {}", id);
                                                                            //4
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StandardError(Arrays.asList("Não existe proposta cadastrada")));
        }

        Proposta proposta = propostaProcurada.get();

        //5
        if(!proposta.verificarSeOEmailDoTokenEOMesmoDaProposta(token)){
            logger.warn("[CADASTRO DE AVISO] O email não token não corresponde ao cadastrado na proposta. Id procurado: {}", id);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new StandardError(Arrays.asList("Proposta não pertencente ao solicitante")));
        }

        logger.warn("[CONSULTA DE PROPOSTA] Proposta encontrada, id consultado: {}", id);

        //6
        PropostaResponse response = new PropostaResponse(proposta);
        return ResponseEntity.ok(response);
    }
}
