package br.com.zup.proposta.controllers.apiResponses;

public class KeycloakUser {
    
    private String id;
    private String username;

    @Deprecated
    public KeycloakUser() {}

    public KeycloakUser(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }
    
}
