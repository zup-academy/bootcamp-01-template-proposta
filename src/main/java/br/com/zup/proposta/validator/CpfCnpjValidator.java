package br.com.zup.proposta.validator;

import br.com.zup.proposta.annotations.CpfCnpj;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class CpfCnpjValidator implements ConstraintValidator<CpfCnpj, CharSequence> {

    @Override
    public boolean isValid(CharSequence valor, ConstraintValidatorContext context) {

        if(valor == null) {
            return true;
        }

        CNPJValidator cnpjValidator = new CNPJValidator();
        CPFValidator cpfValidator = new CPFValidator();

        cnpjValidator.initialize(null);
        cpfValidator.initialize(null);

        return cnpjValidator.isValid(valor, context)
                || cpfValidator.isValid(valor, context);
    }
}
