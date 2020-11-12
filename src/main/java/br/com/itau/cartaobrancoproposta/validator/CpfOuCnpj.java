package br.com.itau.cartaobrancoproposta.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CpfOuCnpjValidator.class)
public @interface CpfOuCnpj {

    String message() default "inválido. Padrão: XXX.XXX.XXX-XX ou XX.XXX.XXX/XXXX-XX";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
