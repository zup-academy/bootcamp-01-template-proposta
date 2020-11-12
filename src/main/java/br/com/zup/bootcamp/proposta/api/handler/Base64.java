package br.com.zup.bootcamp.proposta.api.handler;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Constraint(validatedBy = VerificaBase64Validator.class)
public @interface Base64 {

    String message() default "não é válida";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
