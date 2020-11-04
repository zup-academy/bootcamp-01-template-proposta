package br.com.zup.proposta.exceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import feign.FeignException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CapturaExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex
            , HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        ErrorResponse errorResponse = getErrorResponse(status,
                getErrors(fieldErrors)); //1 //2

        return new ResponseEntity(errorResponse, status);
    }

    @Override
    protected ResponseEntity handleBindException(BindException ex, HttpHeaders headers,
                                                         HttpStatus status, WebRequest request) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        ErrorResponse errorResponse = getErrorResponse(status,
                getErrors(fieldErrors));

        return new ResponseEntity(errorResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        InvalidFormatException invalidFormatException = (InvalidFormatException) ex.getCause();

        List<ErrorObject> fieldErrors = List.of();

        return new ResponseEntity(new ErrorResponse(invalidFormatException.getValue() +
                " Não é um formato válido",
                status.value(), status.getReasonPhrase(), fieldErrors), status);
    }

    private ErrorResponse getErrorResponse(HttpStatus status,
                                           List<ErrorObject> errors){
        return new ErrorResponse("Requisição possui campos inválidos:",
                status.value(), status.getReasonPhrase(), errors);
    }

    private List<ErrorObject> getErrors(List<FieldError> ex){

        return ex.stream()
                .map(error -> new ErrorObject(error.getDefaultMessage(), error.getField(),
                        error.getRejectedValue()))
                .collect(Collectors.toList()); //3 //4
    }

}
