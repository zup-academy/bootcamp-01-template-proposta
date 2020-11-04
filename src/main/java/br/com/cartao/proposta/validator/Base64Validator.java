package br.com.cartao.proposta.validator;

import br.com.cartao.proposta.domain.request.NovaBiometriaRequest;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Base64;

public class Base64Validator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return NovaBiometriaRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        NovaBiometriaRequest novaBiometriaRequest = (NovaBiometriaRequest) o;

        novaBiometriaRequest.getBiometrias().forEach(printfingerRequest -> {
            try{
                byte[] decode = Base64.getDecoder().decode(printfingerRequest.getDigital().getBytes());
                String decodificado = new String(decode);
            }catch (Exception exception){
                errors.rejectValue("fingerprint", null, "não esta codificado Base64");
            }

        });

    }
}
