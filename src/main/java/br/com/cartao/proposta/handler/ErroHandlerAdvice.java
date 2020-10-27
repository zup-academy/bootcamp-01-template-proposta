package br.com.cartao.proposta.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ErroHandlerAdvice {

    private static Logger logger = LoggerFactory.getLogger(ErroHandlerAdvice.class);


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> erroValidacao(MethodArgumentNotValidException exception){
        List<String> todosErrosValidacao = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        fieldErrors.forEach(fieldError -> {
            String mensagem = fieldError.getField() + " "+ fieldError.getDefaultMessage();
            todosErrosValidacao.add(mensagem);
        });

        ErroPadraoApi erroPadraoApi = new ErroPadraoApi(HttpStatus.BAD_REQUEST.toString(), todosErrosValidacao, exception.getMessage());

        logger.warn("Erro de validação!!");

        return ResponseEntity.badRequest().body(erroPadraoApi);
    }

}
