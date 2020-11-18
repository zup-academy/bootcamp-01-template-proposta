package br.com.cartao.proposta.annotation;

import br.com.cartao.proposta.validator.CpfOuCnpjValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CpfOuCnpjValidator.class)
public @interface CpfOuCnpj {

    String message() default "{javax.validation.constraints.CpfOuCnpj.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
