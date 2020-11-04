package br.com.zup.proposta.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class Base64Validator implements ConstraintValidator<Base64, String> {

    @Override
    public void initialize(Base64 constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !value.isBlank() && org.apache.tomcat.util.codec.binary.Base64.isBase64(value);
    }
}
