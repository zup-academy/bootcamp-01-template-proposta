package com.github.marcoscoutozup.proposta.proposta;

import com.github.marcoscoutozup.proposta.analisefinanceira.AnaliseFinanceiraService;
import com.github.marcoscoutozup.proposta.exception.StandardError;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/proposta")
public class PropostaController {

                //1
    private PropostaRepository propostaRepository;
                //2
    private AnaliseFinanceiraService analiseFinanceiraService;
    private Logger logger;

    public PropostaController(PropostaRepository propostaRepository, AnaliseFinanceiraService analiseFinanceiraService, Logger logger) {
        this.propostaRepository = propostaRepository;
        this.analiseFinanceiraService = analiseFinanceiraService;
        this.logger = logger;
    }

    @PostMapping                                                  //3
    public ResponseEntity cadastrarProposta(@RequestBody @Valid PropostaDTO dto, UriComponentsBuilder uri){

        Optional<Proposta> response = propostaRepository.findByDocumento(dto.getDocumento());

        //4
        if(response.isPresent()) {
            logger.warn("Tentativa de criação de proposta com o mesmo documento: " + dto.getDocumento());
                                                                    //5
            return ResponseEntity.unprocessableEntity().body(new StandardError(Arrays.asList("Já existe uma proposta cadastrada com este documento")));
        }

            //6
        Proposta proposta = dto.toProposta();
        propostaRepository.save(proposta);

        logger.info("Proposta criada com sucesso: " + proposta.toString());

        analiseFinanceiraService.processarAnaliseFinanceiraDaProposta(proposta);
        propostaRepository.save(proposta);

        logger.info("Análise financeira da proposta realizada: " + proposta.toString());

        return ResponseEntity
                .created(uri.path("/proposta/{id}")
                .buildAndExpand(proposta.getId()).toUri())
                .build();
    }
}
