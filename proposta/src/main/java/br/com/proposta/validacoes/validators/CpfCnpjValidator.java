package br.com.proposta.validacoes.validators;

import br.com.proposta.validacoes.interfaces.CpfCnpj;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CpfCnpjValidator implements ConstraintValidator<CpfCnpj, CharSequence> {


    @Override
    public boolean isValid(CharSequence charSequence, ConstraintValidatorContext constraintValidatorContext) {

        if(charSequence == null) {

            return true;

        }


        CNPJValidator cnpjValidator = new CNPJValidator();

        CPFValidator cpfValidator = new CPFValidator();


        cnpjValidator.initialize(null);

        cpfValidator.initialize(null);


        return cnpjValidator.isValid(charSequence, constraintValidatorContext)
                || cpfValidator.isValid(charSequence, constraintValidatorContext);


    }
}
