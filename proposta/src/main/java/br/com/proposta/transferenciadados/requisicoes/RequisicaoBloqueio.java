package br.com.proposta.transferenciadados.requisicoes;

import javax.validation.constraints.NotBlank;

public class RequisicaoBloqueio {

    @NotBlank
    private String sistemaResponsavel;

    public RequisicaoBloqueio(@NotBlank String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }

    public void setSistemaResponsavel(String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }
}
