package com.github.marcoscoutozup.proposta.exception;

import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerExceptionAdvice {

    @Autowired
    private MessageSource messageSource;

    private Logger logger = LoggerFactory.getLogger(ControllerExceptionAdvice.class);;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handlerMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<String> errors = e.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> messageSource.getMessage(error, Locale.getDefault()))
                .collect(Collectors.toList());

        StandardError standardError = new StandardError(errors);

        logger.warn("[TRATAMENTO DE ERRO] Tratando erro(s) de MethodArgumentNotValidException: {}", standardError);

        return ResponseEntity.badRequest().body(standardError);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handlerConstraintViolationException(ConstraintViolationException e){
        StandardError standardError = new StandardError(Arrays.asList(e.getLocalizedMessage().split(":")[1].trim()));
        logger.warn("[TRATAMENTO DE ERRO] Tratando erro(s) de ConstraintViolationException: {}", standardError);
        return ResponseEntity.badRequest().body(standardError);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity handlerFeignException(FeignException e) {
        StandardError standardError = new StandardError(Arrays.asList("Erro de comunicação com outros sistemas: " + HttpStatus.valueOf(e.status())));
        logger.warn("[TRATAMENTO DE ERRO] Tratando erro(s) de FeignException: {}", standardError);
        return ResponseEntity.badRequest().body(standardError);
    }

}
