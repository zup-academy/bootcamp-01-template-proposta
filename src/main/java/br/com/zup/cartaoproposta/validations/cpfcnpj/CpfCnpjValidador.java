package br.com.zup.cartaoproposta.validations.cpfcnpj;

import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Contagem de carga intrínseca da classe: 4
 */

public class CpfCnpjValidador implements ConstraintValidator<CpfCnpj, String> {

    @Override
    public void initialize(CpfCnpj constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

        if (value == null) {
            return true;
        }

        //Remover caracteres não numéricos
        value = value.replaceAll("[^0-9]","");

        //Tamanho da string. CPF tamanho 11 e CNPJ tamanho 14
        if (value.length() != 11 && value.length() != 14) {
            return false;
        }

        //CPFs com números iguais
        if (value.equals("00000000000")
                || value.equals("11111111111")
                || value.equals("22222222222")
                || value.equals("33333333333")
                || value.equals("44444444444")
                || value.equals("55555555555")
                || value.equals("66666666666")
                || value.equals("77777777777")
                || value.equals("88888888888")
                || value.equals("99999999999")) {
            return false;
        }

        //CNPJs com números iguais
        if (value.equals("00000000000000")
                || value.equals("11111111111111")
                || value.equals("22222222222222")
                || value.equals("33333333333333")
                || value.equals("44444444444444")
                || value.equals("55555555555555")
                || value.equals("66666666666666")
                || value.equals("77777777777777")
                || value.equals("88888888888888")
                || value.equals("99999999999999")) {
            return false;
        }

        CPFValidator cpfValidator = new CPFValidator();
        cpfValidator.initialize(null);

        CNPJValidator cnpjValidator = new CNPJValidator();
        cnpjValidator.initialize(null);

        return cpfValidator.isValid(value, null)
                || cnpjValidator.isValid(value, null);
    }
}
