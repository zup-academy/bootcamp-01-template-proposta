package com.github.marcoscoutozup.proposta.proposta;

import com.github.marcoscoutozup.proposta.analisefinanceira.AnaliseFinanceiraService;
import com.github.marcoscoutozup.proposta.exception.StandardError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/propostas")
public class CadastrarPropostaController {

                //1
    private PropostaRepository propostaRepository;
                //2
    private AnaliseFinanceiraService analiseFinanceiraService;
    private Logger logger;

    public CadastrarPropostaController(PropostaRepository propostaRepository, AnaliseFinanceiraService analiseFinanceiraService) {
        this.propostaRepository = propostaRepository;
        this.analiseFinanceiraService = analiseFinanceiraService;
        this.logger = LoggerFactory.getLogger(CadastrarPropostaController.class);;
    }

    @PostMapping                                                  //3
    public ResponseEntity cadastrarProposta(@RequestBody @Valid PropostaDTO dto, UriComponentsBuilder uri){

        Optional<Proposta> response = propostaRepository.findByDocumento(dto.getDocumento());

        //4
        if(response.isPresent()) {
            logger.warn("[CRIAÇÃO DA PROPOSTA] Tentativa de criação de proposta com o mesmo documento: {}", dto.getDocumento());
                                                                    //5
            return ResponseEntity.unprocessableEntity().body(new StandardError(Arrays.asList("Já existe uma proposta cadastrada com este documento")));
        }

            //6
        Proposta proposta = dto.toProposta();
        propostaRepository.save(proposta);

        logger.info("[CRIAÇÃO DA PROPOSTA] Proposta criada com sucesso: {}", proposta.toString());

        analiseFinanceiraService.processarAnaliseFinanceiraDaProposta(proposta);
        propostaRepository.save(proposta);

        logger.info("[ANÁLISE FINANCEIRA] Análise financeira da proposta realizada: {}", proposta.toString());

        return ResponseEntity
                .created(uri.path("/propostas/{id}")
                .buildAndExpand(proposta.getId()).toUri())
                .build();
    }
}
