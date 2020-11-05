package br.com.cartao.proposta.domain.dto;

import br.com.cartao.proposta.domain.enums.CarteiraDigitalTipo;
import br.com.cartao.proposta.domain.model.Cartao;
import br.com.cartao.proposta.domain.model.CarteiraDigital;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CarteiraDigitalDto {

    @Email
    @NotBlank
    private String email;
    @NotNull
    private CarteiraDigitalTipo carteira;
    private Cartao cartao;

    @Deprecated
    public CarteiraDigitalDto() {
    }

    public CarteiraDigitalDto(@Email @NotBlank String email, @NotBlank CarteiraDigitalTipo carteira, Cartao cartao) {
        this.email = email;
        this.carteira = carteira;
        this.cartao = cartao;
    }

    public String getEmail() {
        return email;
    }

    public CarteiraDigitalTipo getCarteira() {
        return carteira;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public String numeroCartao(){
        return this.cartao.getCartaoId();
    }

    public CarteiraDigital toModel(){
        return new CarteiraDigital(this.email,this.carteira,this.cartao);
    }

    public String getCartaoId() {
        return this.cartao.getId();
    }
}
