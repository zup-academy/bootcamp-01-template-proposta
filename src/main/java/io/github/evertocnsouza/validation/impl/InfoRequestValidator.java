package io.github.evertocnsouza.validation.impl;

import io.github.evertocnsouza.validation.annotation.InfoRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class InfoRequestValidator implements ConstraintValidator<InfoRequest, HttpServletRequest> {

    @Override
    public void initialize(InfoRequest constraintAnnotation) {
    }

    @Override
    public boolean isValid(HttpServletRequest httpServletRequest, ConstraintValidatorContext context) {
        return !httpServletRequest.getRemoteAddr().isBlank() && !httpServletRequest.getHeader("User-Agent").isBlank();
    }
}
