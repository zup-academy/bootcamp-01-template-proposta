package br.com.zup.proposta.model;

import br.com.zup.proposta.model.enums.TipoCarteira;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class Carteira {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @ManyToOne
    private @NotNull @Valid Cartao cartao;
    private @NotBlank String email;
    @Enumerated(EnumType.STRING)
    private @NotNull TipoCarteira tipoCarteira;

    @Deprecated
    public Carteira(){}

    public Carteira(@NotNull @Valid Cartao cartao, @NotBlank String email,
                    @NotNull TipoCarteira tipoCarteira) {
        this.cartao = cartao;
        this.email = email;
        this.tipoCarteira = tipoCarteira;
    }

    public UUID getId() {
        return id;
    }

    public String numeroCartao(){
        return this.cartao.getNumero();
    }

    public boolean tipoDeCarteiraJaAssociada(TipoCarteira tipoCarteira) {
        return tipoCarteira.equals(this.tipoCarteira);
    }

    @Override
    public String toString() {
        return "Carteira{" +
                "email='" + email + '\'' +
                ", tipoCarteira=" + tipoCarteira +
                '}';
    }


}
