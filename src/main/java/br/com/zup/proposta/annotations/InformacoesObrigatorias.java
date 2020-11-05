package br.com.zup.proposta.annotations;

import br.com.zup.proposta.validator.InformacoesObrigatoriasValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(PARAMETER)
@Retention(RUNTIME)
@Constraint(validatedBy = InformacoesObrigatoriasValidator.class)
public @interface InformacoesObrigatorias {

    String message() default "Favor passar as informações obrigatórias";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
