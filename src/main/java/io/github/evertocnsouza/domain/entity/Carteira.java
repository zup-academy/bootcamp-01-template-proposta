package io.github.evertocnsouza.domain.entity;

import io.github.evertocnsouza.domain.enums.TipoCarteira;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Email
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoCarteira tipoCarteira;

    @Deprecated
    public Carteira() {
    }

    public Carteira(@NotBlank @Email String email, @NotNull TipoCarteira tipoCarteira) {
        this.email = email;
        this.tipoCarteira = tipoCarteira;
    }

    public boolean CarteiraJaAdicionada(TipoCarteira tipoCarteira){
        return this.tipoCarteira.equals(tipoCarteira);
    }

    public Long getId() {
        return id;
    }
}