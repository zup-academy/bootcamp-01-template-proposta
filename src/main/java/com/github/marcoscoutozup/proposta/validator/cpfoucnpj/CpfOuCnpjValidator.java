package com.github.marcoscoutozup.proposta.validator.cpfoucnpj;

import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CpfOuCnpjValidator implements ConstraintValidator<CpfOuCnpj, String> {

    private CPFValidator cpfValidator;
    private CNPJValidator cnpjValidator;

    @Override
    public void initialize(CpfOuCnpj constraintAnnotation) {
        cpfValidator = new CPFValidator();
        cnpjValidator = new CNPJValidator();
        cpfValidator.initialize(null);
        cnpjValidator.initialize(null);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return cpfValidator.isValid(s, constraintValidatorContext) || cnpjValidator.isValid(s, constraintValidatorContext);
    }
}
