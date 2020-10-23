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

    String message() default "A request de bloqueio de cartão é inválida";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
