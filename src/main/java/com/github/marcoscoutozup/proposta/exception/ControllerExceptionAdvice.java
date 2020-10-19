package com.github.marcoscoutozup.proposta.exception;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerExceptionAdvice {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private Logger logger;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handlerMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<String> errors = e.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> messageSource.getMessage(error, Locale.getDefault()))
                .collect(Collectors.toList());

        StandardError standardError = new StandardError(errors);

        logger.warn("Tratando erro(s) de MethodArgumentNotValidException: " + standardError);

        return ResponseEntity.badRequest().body(standardError);
    }

}
