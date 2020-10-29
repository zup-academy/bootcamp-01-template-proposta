package dev.arielalvesdutra.proposta.annotations;

import dev.arielalvesdutra.proposta.annotations.validators.Base64Validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;


@Documented
@Constraint(validatedBy = Base64Validator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ FIELD })
public @interface Base64 {
    String message() default "Documento inválido!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
