package com.github.marcoscoutozup.proposta.validator.cpfoucnpj;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = CpfOuCnpjValidator.class)
public @interface CpfOuCnpj {

    String message() default "O documento é inválido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
