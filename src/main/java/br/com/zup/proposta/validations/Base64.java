package br.com.zup.proposta.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {Base64Validator.class})
public @interface Base64 {

    String message() default "{br.com.zup.proposta.validations.Base64}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
