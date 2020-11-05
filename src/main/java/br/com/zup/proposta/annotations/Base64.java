package br.com.zup.proposta.annotations;

import br.com.zup.proposta.validator.Base64Validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = Base64Validator.class)
public @interface Base64 {

    String message() default "A informação é inválida";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}