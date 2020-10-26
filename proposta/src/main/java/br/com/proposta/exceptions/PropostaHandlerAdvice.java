package br.com.proposta.exceptions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;

@RestControllerAdvice
public class PropostaHandlerAdvice {


    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErroPadrao> handleResponseStatusException(ResponseStatusException responseStatusException) {

        Collection<String> mensagens = new ArrayList<>();

        mensagens.add(responseStatusException.getReason());

        ErroPadrao erroPadronizado = new ErroPadrao(mensagens);

        return ResponseEntity.status(responseStatusException.getStatus()).body(erroPadronizado);

    }

}
