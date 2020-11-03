package br.com.zup.proposta.model.cartao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Biometria {
    
    @Id @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotNull @Column(columnDefinition = "text")
    @Pattern(regexp = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$")
    private String biometria;
    @NotNull @ManyToOne
    private Cartao cartao;

    @Deprecated
    public Biometria(){}

    public Biometria(String biometria, Cartao cartao) {
        this.biometria = biometria;
        this.cartao = cartao;
    }

    public String getId() {
        return this.id;
    }

    public String getBiometria() {
        return this.biometria;
    }

    public Cartao getCartao() {
        return this.cartao;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", biometria='" + getBiometria() + "'" +
            ", cartao='" + getCartao() + "'" +
            "}";
    }

}
