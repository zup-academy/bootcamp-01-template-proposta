package br.com.zup.proposta.dto;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class NovaBiometriaRequest {

    @NotNull
    private MultipartFile digital;

    @Deprecated
    public NovaBiometriaRequest(){}

    public MultipartFile getDigital() {
        return digital;
    }

    public void setDigital(MultipartFile digital) {
        this.digital = digital;
    }

}
