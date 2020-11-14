package com.github.marcoscoutozup.proposta.validator.base64;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.apache.tomcat.util.codec.binary.Base64.isBase64;

public class Base64Validator implements ConstraintValidator<Base64, String> {

    @Override
    public void initialize(Base64 constraintAnnotation) {
    }

    @Override
    public boolean isValid(String valor, ConstraintValidatorContext constraintValidatorContext) {
        return valor != null && isBase64(valor);
    }
}
