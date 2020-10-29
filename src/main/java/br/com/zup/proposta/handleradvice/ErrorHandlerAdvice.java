package br.com.zup.proposta.handleradvice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestControllerAdvice
public class ErrorHandlerAdvice {

    @Autowired
    private MessageSource messageSource;

    private static final Logger log = LoggerFactory.getLogger(ErrorHandlerAdvice.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidationErrorsOutputDto handleMethodNotValid
            (MethodArgumentNotValidException exception) {
        List<ObjectError> globalErrors = exception.getBindingResult().getGlobalErrors();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        return buildValidationErrors(globalErrors, fieldErrors);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ValidationErrorsOutputDto> handleResponseStatus
            (ResponseStatusException exception) {
        List<ObjectError> globalErrors = List.of(new ObjectError("",
                exception.getReason() != null ? exception.getReason()
                        : "Os dados enviados não puderam ser processados"));
        List<FieldError> fieldErrors = List.of();
        ValidationErrorsOutputDto output = buildValidationErrors(globalErrors, fieldErrors);
        return ResponseEntity.status(exception.getStatus()).body(output);
    }

    private ValidationErrorsOutputDto buildValidationErrors
            (List<ObjectError> globalErrors, List<FieldError> fieldErrors) {
        ValidationErrorsOutputDto validationErrors = new ValidationErrorsOutputDto();
        globalErrors.forEach(error -> validationErrors.addError(getErrorMessage(error)));
        fieldErrors.forEach(error -> {
            String errorMessage = getErrorMessage(error);
            validationErrors.addFieldError(error.getField(), errorMessage);
        });
        return validationErrors;
    }

    private String getErrorMessage(ObjectError error) {
        return messageSource.getMessage(error, LocaleContextHolder.getLocale());
    }
}
