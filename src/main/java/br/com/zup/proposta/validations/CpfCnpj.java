package br.com.zup.proposta.validations;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CpfCnpjValidator.class})
public @interface CpfCnpj {

    String message() default "{br.com.zup.proposta.validations.CpfCnpj}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
