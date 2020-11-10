package com.proposta.inclusaonacarteira;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Carteiras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Email
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoCarteira carteira;

    private LocalDateTime associadaEm = LocalDateTime.now();

    @Deprecated
    public Carteiras() {

    }

    public Carteiras(@NotBlank @Email String email, @NotNull TipoCarteira carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public Long getId() {
        return id;
    }

    public boolean verificarCarteira(TipoCarteira tipoCarteira) {
        return this.carteira.equals(tipoCarteira);
    }
}
