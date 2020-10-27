package br.com.proposta.controllers;

import br.com.proposta.dtos.requests.SolicitacaoAnalise;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class IntegracoesController {

    private AtomicInteger contDocumentos = new AtomicInteger();

    @PostMapping(value = "/api/solicitacao")
    public String avaliaDocumento(@RequestBody SolicitacaoAnalise request) {

        int contAtual = contDocumentos.getAndIncrement();

        if (contAtual % 2 != 0) {
            return "COM_RESTRICAO";
        }

        return "SEM_RESTRICAO";

    }

}
