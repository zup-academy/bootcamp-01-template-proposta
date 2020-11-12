package br.com.zup.bootcamp.proposta.api.handler;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CarteiraValidaValidator implements ConstraintValidator<CarteiraValida, String> {

    @Override
    public boolean isValid(String carteira, ConstraintValidatorContext constraintValidatorContext) {
        if (carteira == null) {
            return true;
        }
        return carteira.contentEquals("PAYPAL") || carteira.contentEquals("SAMSUNG_PAY");
    }
}
