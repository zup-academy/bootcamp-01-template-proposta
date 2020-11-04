package br.com.zup.proposta.controllers.form;

public class SolicitaBloqueioForm {
    
    private String sistemaResponsavel;

    public SolicitaBloqueioForm(String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }

    public String getSistemaResponsavel() {
        return this.sistemaResponsavel;
    }

}
