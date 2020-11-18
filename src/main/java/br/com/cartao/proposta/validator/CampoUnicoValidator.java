package br.com.cartao.proposta.validator;

import br.com.cartao.proposta.annotation.CampoUnico;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class CampoUnicoValidator implements ConstraintValidator<CampoUnico, String> {

    private Class<?> className;
    private String fieldName;

    @PersistenceContext
    private final EntityManager manager;

    public CampoUnicoValidator(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public void initialize(CampoUnico constraintAnnotation) {
        className = constraintAnnotation.className();
        fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        List<?> campoBusca = manager.createQuery("select c from " + className.getName() + " c where c." + fieldName + " =: pValue")
                .setParameter("pValue", value)
                .getResultList();


        return !(campoBusca.size()>0);
    }

}
