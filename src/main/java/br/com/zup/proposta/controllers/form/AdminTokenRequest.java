package br.com.zup.proposta.controllers.form;

public class AdminTokenRequest {
    
    private String client_id;
    private String client_secret;
    private String grant_type;

    public AdminTokenRequest(String client_id, String client_secret, String grant_type) {
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.grant_type = grant_type;
    }

    public String getClient_id() {
        return this.client_id;
    }

    public String getClient_secret() {
        return this.client_secret;
    }

    public String getGrant_type() {
        return this.grant_type;
    }

}
