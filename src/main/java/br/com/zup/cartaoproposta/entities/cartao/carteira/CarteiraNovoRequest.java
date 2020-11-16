package br.com.zup.cartaoproposta.entities.cartao.carteira;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Contagem de carga intrínseca da classe: 1
 */

public class CarteiraNovoRequest {

    @NotBlank
    @Email
    private String email;
    @NotNull
    //1
    private Emissor carteira;

    @Deprecated
    public CarteiraNovoRequest(){}

    public CarteiraNovoRequest(@NotBlank String email, @NotNull Emissor carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public String getEmail() {
        return email;
    }

    public Emissor getCarteira() {
        return carteira;
    }
}
