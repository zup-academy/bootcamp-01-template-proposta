package br.com.itau.cartaobrancoproposta.validator;

import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CpfOuCnpjValidator implements ConstraintValidator<CpfOuCnpj, String> {

    @Override
    public void initialize(CpfOuCnpj constraintAnnotation) {

    }

    @Override
    public boolean isValid(String valor, ConstraintValidatorContext context) {
        if (valor.length() == 14) {
            CPFValidator cpfValidator = new CPFValidator();
            cpfValidator.initialize(null);
            return cpfValidator.isValid(valor, null);
        }

        if (valor.length() == 18) {
            CNPJValidator cnpjValidator = new CNPJValidator();
            cnpjValidator.initialize(null);
            return cnpjValidator.isValid(valor, null);
        }

        return false;
    }
}
