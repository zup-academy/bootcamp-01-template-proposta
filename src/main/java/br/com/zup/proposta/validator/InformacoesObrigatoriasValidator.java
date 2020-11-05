package br.com.zup.proposta.validator;

import br.com.zup.proposta.annotations.InformacoesObrigatorias;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class InformacoesObrigatoriasValidator implements ConstraintValidator<InformacoesObrigatorias, HttpServletRequest> {
    @Override
    public void initialize(InformacoesObrigatorias constraintAnnotation) {

    }

    @Override
    public boolean isValid(HttpServletRequest request, ConstraintValidatorContext constraintValidatorContext) {
        return !request.getRemoteAddr().isBlank() && !request.getHeader("User-Agent").isBlank();
    }
}
