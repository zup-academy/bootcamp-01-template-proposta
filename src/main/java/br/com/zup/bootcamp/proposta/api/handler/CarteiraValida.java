package br.com.zup.bootcamp.proposta.api.handler;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = {CarteiraValidaValidator.class})
@Retention(RUNTIME)
public @interface CarteiraValida {

    String message() default "Invalida! Carteiras disponiveis: PAYPAL e SAMSUNG_PAY";
    Class<? extends Payload>[] payload() default { };
    Class<?>[] groups() default { };
}
