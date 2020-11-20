package br.com.zup.proposta.validations;

import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CpfCnpjValidator implements ConstraintValidator<CpfCnpj, CharSequence> {

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {

        //essa verificação é necessária?
        /*if(value == null){
            return true;
        }*/

        CPFValidator cpfValidator = new CPFValidator();
        CNPJValidator cnpjValidator = new CNPJValidator();

        cpfValidator.initialize(null);
        cnpjValidator.initialize(null);

        return cpfValidator.isValid(value, context) ||
                cnpjValidator.isValid(value, context);
    }

}
