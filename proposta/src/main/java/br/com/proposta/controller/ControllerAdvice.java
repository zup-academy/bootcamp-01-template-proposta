package br.com.proposta.controller;

import java.util.List;

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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import br.com.proposta.dto.ValidationErrorsOutputDto;
import feign.FeignException;

@RestControllerAdvice
public class ControllerAdvice {

	@Autowired
    private MessageSource messageSource;
	
	private static final Logger logger = LoggerFactory.getLogger(ControllerAdvice.class);
	
	@ExceptionHandler(FeignException.class)
	public ResponseEntity<?> handleFeignException(FeignException exception) {
		logger.info("Feign Exception - Status {}, Cause {}", exception.status(), exception.getCause());
		return handleCustomStatusException(new ResponseStatusException(HttpStatus.valueOf(exception.status())));
	}
	
	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<?> handleFeignException(HttpClientErrorException exception) {
		return handleCustomStatusException(new ResponseStatusException(exception.getStatusCode()));
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ValidationErrorsOutputDto handleValidationError(
			MethodArgumentNotValidException exception) {

		List<ObjectError> globalErrors = exception.getBindingResult()
				.getGlobalErrors();
		List<FieldError> fieldErrors = exception.getBindingResult()
				.getFieldErrors();

		return buildValidationErrors(globalErrors, fieldErrors);
	}
	
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<?> handleCustomStatusException(ResponseStatusException exception) {
		List<ObjectError> globalErrors = List.of(new ObjectError("",exception.getReason() != null ? exception.getReason(): "Os dados enviados não puderam ser processados"));

		List<FieldError> fieldErrors = List.of();
		ValidationErrorsOutputDto output = buildValidationErrors(globalErrors,fieldErrors);

		return ResponseEntity.status(exception.getStatus()).body(output);
	}


	private ValidationErrorsOutputDto buildValidationErrors(
			List<ObjectError> globalErrors, List<FieldError> fieldErrors) {
		ValidationErrorsOutputDto validationErrors = new ValidationErrorsOutputDto();

		globalErrors.forEach(
				error -> validationErrors.addError(getErrorMessage(error)));

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
