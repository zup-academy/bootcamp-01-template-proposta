package br.com.zup.bootcamp.proposta.api.handler;

import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class VerificaDocumentoCpfCnpjValidator implements ConstraintValidator<CPForCNPJ, CharSequence> {

    @Override
    public boolean isValid(CharSequence documento, ConstraintValidatorContext context) {
        if (documento == null) {
            return true;
        }

        CPFValidator cpfValidator = new CPFValidator();
        cpfValidator.initialize(null);

        CNPJValidator cnpjValidator = new CNPJValidator();
        cnpjValidator.initialize(null);

        return cpfValidator.isValid(documento, context) || cnpjValidator.isValid(documento, context);
    }
}
