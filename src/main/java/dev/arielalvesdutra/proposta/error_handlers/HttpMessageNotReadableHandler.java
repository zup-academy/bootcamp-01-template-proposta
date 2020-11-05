package dev.arielalvesdutra.proposta.error_handlers;

import dev.arielalvesdutra.proposta.controllers.dtos.ErroResponseDTO;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;


// 1 HandlerAdviceUtils.java
// 2 ErroResponseDTO.java
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class HttpMessageNotReadableHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResponseDTO> handler(
            HttpServletRequest request,
            HttpMessageNotReadableException exception) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErroResponseDTO error = new ErroResponseDTO()
                .setStatus(status.value())
                .setCaminho(request.getRequestURI())
                .setInstante(Instant.now())
                .setErro(status.name())
                .setMensagem("Dados inválidos!");

        return ResponseEntity.status(status).body(error);
    }
}
