package br.com.zup.bootcamp.proposta.api.handler;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = {VerificaDocumentoCpfCnpjValidator.class})
@Retention(RUNTIME)
public @interface CPForCNPJ {

    String message() default "nao é valido";
    Class<? extends Payload>[] payload() default { };
    Class<?>[] groups() default { };

}
