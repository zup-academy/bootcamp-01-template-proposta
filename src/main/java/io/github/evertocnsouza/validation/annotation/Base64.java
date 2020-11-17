package io.github.evertocnsouza.validation.annotation;

import io.github.evertocnsouza.validation.impl.Base64Validator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = Base64Validator.class)
public @interface Base64 {

    String message() default "Caracteres especiais não são aceitos!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}