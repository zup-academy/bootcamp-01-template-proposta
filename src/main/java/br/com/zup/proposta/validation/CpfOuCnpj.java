package br.com.zup.proposta.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CpfOuCnpjValidator.class)
public @interface CpfOuCnpj {

    String message() default "deve ser válido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
