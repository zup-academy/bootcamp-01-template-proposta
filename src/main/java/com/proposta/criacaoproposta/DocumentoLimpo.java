package com.proposta.criacaoproposta;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotBlank;

public class DocumentoLimpo {

    @NotBlank
    private String documento;

    public DocumentoLimpo(@NotBlank String documento) {
        this.documento = documento;
    }

    public String hash() {
        return new BCryptPasswordEncoder().encode(documento);
    }
}
