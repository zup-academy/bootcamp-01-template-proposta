package br.com.zup.proposta.controllers.form;

public class NovoUsuarioForm {
    
    private String username;

    public NovoUsuarioForm(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

}
