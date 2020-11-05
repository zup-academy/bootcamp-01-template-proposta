package io.github.evertocnsouza.rest.dto.request;

import io.github.evertocnsouza.domain.entity.Carteira;
import io.github.evertocnsouza.domain.enums.TipoCarteira;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class CarteiraRequest {

    @NotBlank
    @Email
    private String email;

    public Carteira toCarteira(TipoCarteira carteira){
        return new Carteira(email, carteira);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}