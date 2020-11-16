package br.com.zup.cartaoproposta.validations.cpfcnpj;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CpfCnpjValidador.class)
public @interface CpfCnpj {

    String message() default "documento cpf/cnpj inválido";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
