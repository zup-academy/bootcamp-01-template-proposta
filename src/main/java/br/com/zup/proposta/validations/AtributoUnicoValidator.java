package br.com.zup.proposta.validations;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AtributoUnicoValidator implements ConstraintValidator<AtributoUnico, CharSequence> {

    private String nomeCampo;
    private Class<?> nomeClasse;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void initialize(AtributoUnico constraintAnnotation) {
        this.nomeCampo = constraintAnnotation.nomecampo();
        this.nomeClasse = constraintAnnotation.nomeClasse();
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {

        Query query = entityManager.createQuery("select 1 from "
                + nomeClasse.getName()+
                " where " + nomeCampo + "=:value");
        query.setParameter("value", value);

        return query.getResultList().isEmpty();
    }
}
