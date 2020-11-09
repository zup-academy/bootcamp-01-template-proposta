package br.com.zup.proposta.controllers.form;

public class NovaSenhaUsuarioForm {
    
    private String value;
    private String type;

    public NovaSenhaUsuarioForm(String value) {
        this.value = value;
        this.type = "password";
    }

    public String getValue() {
        return this.value;
    }

    public String getType() {
        return this.type;
    }

}
