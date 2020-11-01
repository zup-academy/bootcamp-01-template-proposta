package br.com.itau.cartaobrancoproposta.controller;

import br.com.itau.cartaobrancoproposta.component.TransacaoDados;
import br.com.itau.cartaobrancoproposta.error.ApiErrorException;
import br.com.itau.cartaobrancoproposta.model.Proposta;
import br.com.itau.cartaobrancoproposta.model.PropostaRequest;
import br.com.itau.cartaobrancoproposta.service.PropostaService;
import br.com.itau.cartaobrancoproposta.validator.VerificaPropostaMesmoSolicitante;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class PropostaController {

    private final Logger logger = LoggerFactory.getLogger(PropostaController.class);
//1
    private final TransacaoDados transacaoDados;
//1
    private final VerificaPropostaMesmoSolicitante verificaProposta;
//1
    private final PropostaService propostaService;

    public PropostaController(TransacaoDados transacaoDados, VerificaPropostaMesmoSolicitante verificaPropostaMesmoSolicitante, PropostaService propostaService) {
        this.transacaoDados = transacaoDados;
        this.propostaService = propostaService;
        this.verificaProposta = verificaPropostaMesmoSolicitante;
    }
//1
    @PostMapping("/v1/proposta")
    public ResponseEntity<?> criaProposta(@RequestBody @Valid PropostaRequest novaPropostaRequest, UriComponentsBuilder builder) {
        if (!verificaProposta.estaValido(novaPropostaRequest.getDocumento())) { //1
            throw new ApiErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "Documento (" + novaPropostaRequest.getDocumento() + ") já está cadastrado"); //1
        }

        Proposta proposta = novaPropostaRequest.toModel(); //1

        transacaoDados.salva(proposta);
        logger.info("Proposta inicial id={} documento={} restrição={} criada com sucesso!", proposta.getId(), proposta.getDocumento(), proposta.getRestricao());
        propostaService.verificaRestricao(proposta);
        transacaoDados.atualiza(proposta);
        logger.info("Proposta id={} documento={} restrição={} criada com sucesso!", proposta.getId(), proposta.getDocumento(), proposta.getRestricao());

        URI enderecoConsulta = builder.path("/v1/proposta/{id}").buildAndExpand(proposta.getId()).toUri();

        return ResponseEntity.created(enderecoConsulta).build();
    }
}
