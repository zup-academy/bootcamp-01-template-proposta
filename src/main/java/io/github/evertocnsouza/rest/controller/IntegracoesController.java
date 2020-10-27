package io.github.evertocnsouza.rest.controller;

import io.github.evertocnsouza.rest.dto.DocumentoRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class IntegracoesController {

    private AtomicInteger contDocumentos = new AtomicInteger();

    @PostMapping(value = "avalia")
    public String avaliaDocumento(@RequestBody DocumentoRequest request) {
        int contAtual = contDocumentos.getAndIncrement();
        if(contAtual % 2 != 0) {
            return "COM_RESTRICAO";
        }
        return "SEM_RESTRICAO";
    }
}
