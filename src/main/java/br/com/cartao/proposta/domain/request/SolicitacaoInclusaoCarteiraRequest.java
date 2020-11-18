package br.com.cartao.proposta.domain.request;

import br.com.cartao.proposta.domain.enums.CarteiraDigitalTipo;
import br.com.cartao.proposta.domain.model.CarteiraDigital;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Carga intrínseca máxima permitida - 9
 * Carga intrínseca da classe - 2
 */

public class SolicitacaoInclusaoCarteiraRequest {

    @Email @NotBlank
    private final String email;
    @NotBlank
    // +1
    private final CarteiraDigitalTipo carteira;

    public SolicitacaoInclusaoCarteiraRequest(String email, CarteiraDigitalTipo carteira) {
        this.email = email;
        this.carteira = carteira;
    }
    // +1
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
