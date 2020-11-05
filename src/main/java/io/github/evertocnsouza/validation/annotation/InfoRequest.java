package io.github.evertocnsouza.validation.annotation;

import io.github.evertocnsouza.validation.impl.InfoRequestValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = InfoRequestValidator.class)
public @interface InfoRequest {

    String message() default "A requisição não contém as informações obrigatórias";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
