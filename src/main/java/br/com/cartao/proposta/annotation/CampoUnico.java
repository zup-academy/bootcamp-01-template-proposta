package br.com.cartao.proposta.annotation;

import br.com.cartao.proposta.validator.CampoUnicoValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CampoUnicoValidator.class)
public @interface CampoUnico {
    String message() default "{}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<?> className();
    String fieldName();
}
