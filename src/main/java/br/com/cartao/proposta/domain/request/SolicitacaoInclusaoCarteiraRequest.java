package br.com.cartao.proposta.domain.request;

import br.com.cartao.proposta.domain.enums.CarteiraDigitalTipo;
import br.com.cartao.proposta.domain.model.CarteiraDigital;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class SolicitacaoInclusaoCarteiraRequest {

    @Email @NotBlank
    private final String email;
    @NotBlank
    private final CarteiraDigitalTipo carteira;

    public SolicitacaoInclusaoCarteiraRequest(String email, CarteiraDigitalTipo carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public SolicitacaoInclusaoCarteiraRequest(CarteiraDigital carteiraDigital){
        this.email = carteiraDigital.getEmail();
        this.carteira = carteiraDigital.getCarteira();
    }

    public String getEmail() {
        return email;
    }

    public CarteiraDigitalTipo getCarteira() {
        return carteira;
    }

}
