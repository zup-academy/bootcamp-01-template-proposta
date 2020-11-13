package br.com.proposta.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;


//Contagem de Pontos - TOTAL:0


public class ValidadorDocumento implements ConstraintValidator<DocumentoValido, String>{
	

	@Override
	public void initialize(DocumentoValido  params) {
	}


	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		CPFValidator cpfValidator = new CPFValidator();
		cpfValidator.initialize(null);
		CNPJValidator cnpjValidator = new CNPJValidator();
		cnpjValidator.initialize(null);

		return cpfValidator.isValid(value, null) || cnpjValidator.isValid(value, null);
	}	

}