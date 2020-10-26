package br.com.zup.bootcamp.proposta.api.handler;

import br.com.zup.bootcamp.proposta.api.dto.RequestPropostaDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class VerificaDocumentoCpfCnpjValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return RequestPropostaDto.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(errors.hasErrors()) {
            return ;
        }

        RequestPropostaDto request = (RequestPropostaDto) target;
        if(!request.documentoValido()) {
            errors.rejectValue("documento",null, "documento precisa ser cpf ou cnpj");
        }
    }
}
