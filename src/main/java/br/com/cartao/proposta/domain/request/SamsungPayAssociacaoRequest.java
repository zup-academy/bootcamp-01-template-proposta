package br.com.cartao.proposta.domain.request;

import br.com.cartao.proposta.domain.dto.CarteiraDigitalDto;
import br.com.cartao.proposta.domain.enums.CarteiraDigitalTipo;
import br.com.cartao.proposta.domain.model.Cartao;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SamsungPayAssociacaoRequest {

    @Email @NotBlank
    private final String email;
    @NotNull
    private final CarteiraDigitalTipo carteira;

    public SamsungPayAssociacaoRequest(String email, String carteira) {
        this.email = email;
        this.carteira = CarteiraDigitalTipo.SAMSUNG_PAY;
    }

    public String getEmail() {
        return email;
    }

    public CarteiraDigitalTipo getCarteira() {
        return carteira;
    }

    public SolicitacaoInclusaoCarteiraRequest toIntegracao(){
        return new SolicitacaoInclusaoCarteiraRequest(this.email, this.carteira);
    }

    public CarteiraDigitalDto toDto(Cartao cartao){
        return new CarteiraDigitalDto(this.email,this.carteira, cartao);
    }


}
