package com.github.marcoscoutozup.proposta.validator.requestbloqueiocartao;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(PARAMETER)
@Retention(RUNTIME)
@Constraint(validatedBy = RequestBloqueioCartaoValidator.class)
public @interface RequestBloqueioCartao  {

    String message() default "A requisição não contém as informações obrigatórias";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
