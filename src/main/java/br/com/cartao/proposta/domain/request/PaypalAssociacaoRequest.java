package br.com.cartao.proposta.domain.request;

import br.com.cartao.proposta.domain.dto.CarteiraDigitalDto;
import br.com.cartao.proposta.domain.enums.CarteiraDigitalTipo;
import br.com.cartao.proposta.domain.model.Cartao;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Carga intrínseca máxima permitida - 9
 * Carga intrínseca da classe - 4
 */

public class PaypalAssociacaoRequest {

    @Email @NotBlank
    private String email;
    // +1
    private CarteiraDigitalTipo carteira = CarteiraDigitalTipo.PAYPAL;

    @Deprecated
    public PaypalAssociacaoRequest() {
    }

    public PaypalAssociacaoRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public CarteiraDigitalTipo getCarteira() {
        return carteira;
    }
    // +1
    public SolicitacaoInclusaoCarteiraRequest toIntegracao(){
        return new SolicitacaoInclusaoCarteiraRequest(this.email, this.carteira);
    }
    // +2
    public CarteiraDigitalDto toDto(Cartao cartao){
        return new CarteiraDigitalDto(this.email,this.carteira, cartao);
    }


}
