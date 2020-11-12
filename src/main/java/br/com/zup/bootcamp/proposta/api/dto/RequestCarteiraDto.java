package br.com.zup.bootcamp.proposta.api.dto;

import br.com.zup.bootcamp.proposta.api.handler.CarteiraValida;
import br.com.zup.bootcamp.proposta.domain.entity.Carteira;
import br.com.zup.bootcamp.proposta.domain.service.enums.EmpresaAssociada;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class RequestCarteiraDto {

    @NotBlank @Email
    private String email;

    @NotBlank @CarteiraValida
    private String carteira;

    public Carteira toEntity(){
        return new Carteira(email, getCarteira());
    }

    public String getEmail() {
        return email;
    }

    public EmpresaAssociada getCarteira() {
        return Enum.valueOf(EmpresaAssociada.class, carteira);
    }

    @Override
    public String toString() {
        return "RequestCarteiraDto{" +
                "email='" + email + '\'' +
                ", carteira='" + carteira + '\'' +
                '}';
    }
}
