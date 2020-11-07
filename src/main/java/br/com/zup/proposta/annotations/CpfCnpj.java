package br.com.zup.proposta.annotations;

import br.com.zup.proposta.validator.CpfCnpjValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {CpfCnpjValidator.class})
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)

public @interface CpfCnpj {

    String message() default "{br.com.zup.casadocodigo.annotations.CpfCnpj" + "message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
