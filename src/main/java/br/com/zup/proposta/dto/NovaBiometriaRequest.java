package br.com.zup.proposta.dto;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class NovaBiometriaRequest {

    @NotBlank
    private String digital;

    public String getDigital() {
        return digital;
    }

    private void setDigital(String digital) {
        this.digital = digital;
    }

}
