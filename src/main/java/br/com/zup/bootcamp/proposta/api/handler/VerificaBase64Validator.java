package br.com.zup.bootcamp.proposta.api.handler;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.apache.tomcat.util.codec.binary.Base64.isBase64;

public class VerificaBase64Validator implements ConstraintValidator<Base64, byte[]> {
     @Override
    public boolean isValid(byte[] base64, ConstraintValidatorContext ctx) {
         if (base64 == null) {
             return true;
         }
        return isBase64(base64);
    }
}
