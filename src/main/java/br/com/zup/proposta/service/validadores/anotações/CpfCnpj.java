package br.com.zup.proposta.service.validadores.anotações;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import br.com.zup.proposta.service.validadores.CpfCnpjValidator;

@Documented
@Constraint(validatedBy = CpfCnpjValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CpfCnpj {
    
    String message() default "O documento deve ter um formato válido.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
