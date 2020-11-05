package br.com.cartao.proposta.validator;

import br.com.cartao.proposta.annotation.CpfOuCnpj;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CpfOuCnpjValidator implements ConstraintValidator<CpfOuCnpj, String> {

    private static Logger logger = LoggerFactory.getLogger(CpfOuCnpjValidator.class);


    @Override
    public void initialize(CpfOuCnpj constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null){
            return true;
        }

        CNPJValidator cnpjValidator = new CNPJValidator();
        CPFValidator cpfValidator= new CPFValidator();

        cnpjValidator.initialize(null);
        cpfValidator.initialize(null);

        String documento = value
                .replace(".", "")
                .replace("-", "")
                .replace("/", "");

        return cnpjValidator.isValid(documento,context) ||
        cpfValidator.isValid(documento, context);

    }
}
