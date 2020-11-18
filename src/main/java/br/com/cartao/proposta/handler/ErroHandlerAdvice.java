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

/**
 * Carga intrínseca máxima permitida -
 * Carga intrínseca da classe - 3
 */

@RestControllerAdvice
public class ErroHandlerAdvice {

    private static Logger logger = LoggerFactory.getLogger(ErroHandlerAdvice.class);


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> erroValidacao(MethodArgumentNotValidException exception){
        List<String> todosErrosValidacao = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        // +1
        fieldErrors.forEach(fieldError -> {
            String mensagem = fieldError.getField() + " "+ fieldError.getDefaultMessage();
            todosErrosValidacao.add(mensagem);
        });
        // +1
        ErroPadraoApi erroPadraoApi = new ErroPadraoApi(HttpStatus.BAD_REQUEST.toString(), todosErrosValidacao, exception.getMessage());

        logger.warn("Erro de validação!!");

        return ResponseEntity.badRequest().body(erroPadraoApi);
    }

    @ExceptionHandler(ErroNegocioException.class)
    // +1
    public ResponseEntity<ErroPadraoApi> handlerErroDeNegocio(ErroNegocioException exception){
        List<String> todosErros = new ArrayList<>();

        todosErros.add(exception.getMensagem());

        ErroPadraoApi erroPadraoApi = new ErroPadraoApi(exception.getHttpStatus().toString(), todosErros, exception.getStackTrace().toString());

        return ResponseEntity.status(exception.getHttpStatus()).body(erroPadraoApi);
    }

}
