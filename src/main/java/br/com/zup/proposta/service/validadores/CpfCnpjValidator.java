package br.com.zup.proposta.service.validadores;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;

import br.com.zup.proposta.service.validadores.anotações.CpfCnpj;

public class CpfCnpjValidator implements ConstraintValidator<CpfCnpj, String> {

    private CPFValidator cpfValidator;
    private CNPJValidator cnpjValidator;

    @Override
    public void initialize(CpfCnpj params) {
        cpfValidator = new CPFValidator();
        cpfValidator.initialize(null);
        cnpjValidator = new CNPJValidator();
        cnpjValidator.initialize(null);
    }
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value.equals("")) return false;
        return cpfValidator.isValid(value, context) || cnpjValidator.isValid(value, context);
    }

}
