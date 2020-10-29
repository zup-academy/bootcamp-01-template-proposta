package com.github.marcoscoutozup.proposta.proposta;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.marcoscoutozup.proposta.analisefinanceira.AnaliseFinanceiraService;
import com.github.marcoscoutozup.proposta.exception.StandardError;
import io.opentracing.Tracer;
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
    private Tracer tracer;
    private Logger logger = LoggerFactory.getLogger(CadastrarPropostaController.class);

    public CadastrarPropostaController(PropostaRepository propostaRepository, AnaliseFinanceiraService analiseFinanceiraService, Tracer tracer) {
        this.propostaRepository = propostaRepository;
        this.analiseFinanceiraService = analiseFinanceiraService;
        this.tracer = tracer;
    }

    @PostMapping                                                  //3
    public ResponseEntity cadastrarProposta(@RequestBody @Valid PropostaRequest propostaRequest, UriComponentsBuilder uri) throws JsonProcessingException {
        tracer.activeSpan().setTag("usuario.email", propostaRequest.getEmail());
        tracer.activeSpan().setBaggageItem("usuario.email", propostaRequest.getEmail());
        tracer.activeSpan().log("Cadastrando a proposta do usuário");

        Optional<Proposta> response = propostaRepository.findByDocumento(Proposta.criptografarDocumento(propostaRequest.getDocumento()));

        //4
        if(response.isPresent()) {
            logger.warn("[CRIAÇÃO DA PROPOSTA] Mais de uma tentativa de criação de proposta com o mesmo documento: {}", propostaRequest.getDocumento());
                                                                    //5
            return ResponseEntity.unprocessableEntity().body(new StandardError(Arrays.asList("Solicitação inválida, mais de uma tentativa de criação de proposta com os mesmos dados")));
        }

            //6
        Proposta proposta = propostaRequest.toProposta();
        propostaRepository.save(proposta);

        logger.info("[CRIAÇÃO DA PROPOSTA] Proposta criada com sucesso: {}", proposta.getId());

        analiseFinanceiraService.processarAnaliseFinanceiraDaProposta(proposta);
        propostaRepository.save(proposta);

        logger.info("[ANÁLISE FINANCEIRA] Análise financeira da proposta realizada: {}", proposta.getId());

        return ResponseEntity
                .created(uri.path("/propostas/{id}")
                .buildAndExpand(proposta.getId()).toUri())
                .build();
    }
}
