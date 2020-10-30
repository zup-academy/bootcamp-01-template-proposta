package br.com.proposta.dtos.requests;

import javax.validation.constraints.NotBlank;

public class BloqueioRequest {

    @NotBlank
    private String sistemaResponsavel;

    public BloqueioRequest(@NotBlank String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }

    public void setSistemaResponsavel(String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }
}
