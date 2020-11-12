package br.com.proposta.testesValidacoes;
import br.com.proposta.entidades.Biometria;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Set;

public class ValidacoesBiometriaTestes {

    /* Validações testadas:

     *  1 - o valor do arquivo convertido para base64 não pode ser nem nulo nem branco
     * */

    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }



    @Test
    @DisplayName("biometria está instanciando sem problemas")
    public void imagemConvertidaParaBase64DeveSerInstanciada() throws IOException {

        /* testando com arquivo de imagem do próprio repositório -> metricas.jpg */

        byte[] imagem =
                FileUtils.readFileToByteArray(new File("/home/marceloamorim/Documentos/bootcamp-01-template-proposta/readme-images/analise_proposta.jpg"));

        String encodedString = Base64
                .getEncoder()
                .encodeToString(imagem);

        var biometria = new Biometria(encodedString);

        Set<ConstraintViolation<Biometria>> violations = validator.validate(biometria);

        Assert.assertTrue(violations.isEmpty());

    }

}
