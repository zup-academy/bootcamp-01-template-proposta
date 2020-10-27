package br.com.proposta.validacoes.validators;

import br.com.proposta.validacoes.interfaces.CpfCnpj;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class CpfCnpjValidator implements ConstraintValidator<CpfCnpj, String> {

    @Override
    public boolean isValid(String identificacao, ConstraintValidatorContext constraintValidatorContext) {


        char[] caracteres = new char[identificacao.length()];

        for (int i = 0; i < identificacao.length(); i++) {
            caracteres[i] = identificacao.charAt(i);
        }

        boolean cpfCondicao = caracteres[3] == '.' && caracteres[7] == '.' && caracteres[11] == '-';
        boolean cnpjCondicao = caracteres[2] == '.' && caracteres[6] ==
                '.' && caracteres[10] == '/' && caracteres[15] == '-';

        if(cpfCondicao && caracteres.length == 13){

            for(int i = 0; i < identificacao.length(); i++) {
                if(!Character.isDigit(caracteres[i]) && i != 3 && i != 7 && i != 11){
                        return false;
                }
            }

            return true;

        }else if(cnpjCondicao && caracteres.length == 17){

            for(int i = 0; i < identificacao.length(); i++) {
                if(!Character.isDigit(caracteres[i]) && i != 2 && i != 6 && i != 10 && i != 15){
                    return false;
                }
            }

            return true;

        }

        return false;

    }
}
