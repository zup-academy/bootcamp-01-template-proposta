package br.com.zup.proposta.dto;

import br.com.zup.proposta.validations.Base64;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class NovaBiometriaRequest {

    @NotBlank
    @Base64(message = "Formato inválido")
    private String digital;

    @Deprecated
    public NovaBiometriaRequest(){}

    public String getDigital() {
        return digital;
    }

}
