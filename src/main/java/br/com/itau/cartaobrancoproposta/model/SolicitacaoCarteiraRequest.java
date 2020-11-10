package br.com.itau.cartaobrancoproposta.model;

import javax.validation.constraints.NotBlank;

public class SolicitacaoCarteiraRequest {

    @NotBlank
    private final String email;
    @NotBlank
    private final String carteira;

    public SolicitacaoCarteiraRequest(@NotBlank String email, @NotBlank String carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public String getEmail() {
        return email;
    }

    public String getCarteira() {
        return carteira;
    }
}
