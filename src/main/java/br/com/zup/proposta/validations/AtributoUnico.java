package br.com.zup.proposta.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {AtributoUnicoValidator.class})
public @interface AtributoUnico {

    String message() default "{br.com.zup.proposta.validations.CpfCnpj}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String nomecampo();
    Class<?> nomeClasse();

}
