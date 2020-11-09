package br.com.zup.proposta.controllers.apiResponses;

public class TokenResponse {
    
    private String access_token;

    @Deprecated
    public TokenResponse(){}

    public TokenResponse(String access_token) {
        this.access_token = access_token;
    }

    public String getAccess_token() {
        return this.access_token;
    }

    public String getBearer_token() {
        return "Bearer " + this.access_token;
    }
}
