package com.github.marcoscoutozup.proposta.validator.requestbloqueiocartao;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class InformacoesObrigatoriasRequestValidator implements ConstraintValidator<InformacoesObrigatoriasRequest, HttpServletRequest> {

    @Override
    public void initialize(InformacoesObrigatoriasRequest constraintAnnotation) {

    }

    @Override
    public boolean isValid(HttpServletRequest request, ConstraintValidatorContext constraintValidatorContext) {
        return !request.getRemoteAddr().isBlank() && !request.getHeader("User-Agent").isBlank();
    }
}
