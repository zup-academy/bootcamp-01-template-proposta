package br.com.itau.cartaobrancoproposta.controller;

import br.com.itau.cartaobrancoproposta.component.TransacaoDados;
import br.com.itau.cartaobrancoproposta.model.Proposta;
import br.com.itau.cartaobrancoproposta.model.PropostaRequest;
import br.com.itau.cartaobrancoproposta.model.PropostaResponse;
import br.com.itau.cartaobrancoproposta.service.PropostaService;
import br.com.itau.cartaobrancoproposta.validator.VerificaPropostaMesmoSolicitante;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class PropostaController {

    private final Logger logger = LoggerFactory.getLogger(PropostaController.class);
//1
    private final TransacaoDados transacaoDados;
//1
    private final VerificaPropostaMesmoSolicitante verificadorProposta;
//1
    private final PropostaService propostaService;
//1
    private Proposta proposta;

    public PropostaController(TransacaoDados transacaoDados, VerificaPropostaMesmoSolicitante verificaPropostaMesmoSolicitante, PropostaService propostaService) {
        this.transacaoDados = transacaoDados;
        this.propostaService = propostaService;
        this.verificadorProposta = verificaPropostaMesmoSolicitante;
    }

    @PostMapping("/v1/propostas")
    public ResponseEntity<?> criaProposta(@RequestBody @Valid PropostaRequest novaPropostaRequest, UriComponentsBuilder builder) { //1
        novaPropostaRequest.verificaDocumentoValido(verificadorProposta);

        proposta = novaPropostaRequest.toModel();

        transacaoDados.salva(proposta);
        logger.info("Proposta inicial id={} documento={} restrição={} criada com sucesso!", proposta.getId(), proposta.getDocumento(), proposta.getRestricao());
        propostaService.buscaRestricaoProposta(proposta);
        transacaoDados.atualiza(proposta);
        logger.info("Proposta id={} documento={} restrição={} atualizada com sucesso!", proposta.getId(), proposta.getDocumento(), proposta.getRestricao());

        URI enderecoConsulta = builder.path("/v1/propostas/{id}").buildAndExpand(proposta.getId()).toUri();

        return ResponseEntity.created(enderecoConsulta).build();
    }

    @GetMapping("/v1/propostas/{id}")
    public ResponseEntity<PropostaResponse> exibeProposta(@PathVariable("id") String idProposta) { //1
        proposta = transacaoDados.busca(Proposta.class, idProposta);
        if (proposta != null) { //1
            logger.info("Proposta id={} documento={} foi encontrada com sucesso!", proposta.getId(), proposta.getDocumento());
            return ResponseEntity.ok(new PropostaResponse(proposta));
        }

        logger.error("Proposta id={} não foi encontrada.", idProposta);
        return ResponseEntity.badRequest().build();
    }
}
