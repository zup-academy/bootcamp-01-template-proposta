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

public class SamsungPayAssociacaoRequest {

    @Email @NotBlank
    private final String email;
    @NotNull
    // +1
    private final CarteiraDigitalTipo carteira;

    public SamsungPayAssociacaoRequest(String email) {
        this.email = email;
        this.carteira = CarteiraDigitalTipo.SAMSUNG_PAY;
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
