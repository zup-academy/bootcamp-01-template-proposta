package io.github.evertocnsouza.validation.annotation;

import io.github.evertocnsouza.validation.impl.CpfCnpjValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {CpfCnpjValidator.class})
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface CpfCnpj {

    String message() default "{erro.documento}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
