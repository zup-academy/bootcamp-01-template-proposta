package br.com.proposta.transferenciadados.requisicoes;

import javax.validation.constraints.NotBlank;

public class RequisicaoAssociarCarteira {

    @NotBlank
    private String email;

    @NotBlank
    private String carteira;

    @Deprecated
    public RequisicaoAssociarCarteira(){}

    public RequisicaoAssociarCarteira(@NotBlank String email, @NotBlank String carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCarteira() {
        return carteira;
    }

    public void setCarteira(String carteira) {
        this.carteira = carteira;
    }
}
