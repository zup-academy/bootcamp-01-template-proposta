package br.com.zup.proposta.configs;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.zup.proposta.configs.exceptions.ApiException;
import br.com.zup.proposta.configs.exceptions.PropostaDuplicadaException;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroPadronizado> argumentNotValidHandler(MethodArgumentNotValidException exception) {
        Collection<String> mensagens = new ArrayList<>();

        exception.getBindingResult().getFieldErrors().forEach(error -> {
            mensagens.add(String.format("Campo %s %s", error.getField(), error.getDefaultMessage()));
        });

        ErroPadronizado erroPadronizado = new ErroPadronizado(mensagens);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroPadronizado); 
    }

    @ExceptionHandler(PropostaDuplicadaException.class)
    public ResponseEntity<ErroPadronizado> propostaDuplicadaHandler(PropostaDuplicadaException exception) {
        Collection<String> mensagens = new ArrayList<>();

        mensagens.add(exception.getMessage());

        ErroPadronizado erroPadronizado = new ErroPadronizado(mensagens);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(erroPadronizado);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErroPadronizado> erroConversaoBiometria(ApiException exception) {
        Collection<String> mensagens = new ArrayList<>();

        mensagens.add(exception.getMessage());

        ErroPadronizado erroPadronizado = new ErroPadronizado(mensagens);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroPadronizado);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErroPadronizado> objetoNaoEncontrado(IllegalStateException exception) {
        Collection<String> mensagens = new ArrayList<>();

        mensagens.add(exception.getMessage());

        ErroPadronizado erroPadronizado = new ErroPadronizado(mensagens);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroPadronizado);
    }
}
