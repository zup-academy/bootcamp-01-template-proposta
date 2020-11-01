package com.proposta.criacaoproposta;

import com.proposta.feign.AnalisePropostaCliente;
import com.proposta.validator.ValidarDocumentoIgual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private ValidarDocumentoIgual validarDocumentoIgual;

    @Autowired
    private PropostaService propostaService;

    @PostMapping
    @Transactional
    //1 PropostaRequest
    public ResponseEntity<?> cria(@RequestBody @Valid PropostaRequest request, UriComponentsBuilder builder) {
        //2 Proposta
        Proposta proposta = request.toModel();
        //3
        if ((proposta.getDocumento().length()) != 11 && (proposta.getDocumento().length()) != 14) {
            return ResponseEntity.badRequest().body("Documento inválido.");
        }
        //4 //5 ValidarDocumento
        else if (!validarDocumentoIgual.validarDocumento(request)) {
            return ResponseEntity.unprocessableEntity().body("Proposta inadequada.");
        }

        //6 PropostaResponse // 7 Proposta service
        PropostaResponse propostaResponse = propostaService.cria(proposta);
        URI uriCreated = builder.path("/propostas/{id}").build(propostaResponse.getId());
        return ResponseEntity.created(uriCreated).build();
    }
}