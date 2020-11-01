package br.com.zup.proposta.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
public class Cartao {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @NotBlank
    private String numero;
    @OneToOne
    private Proposta proposta; //1

    @Deprecated
    public Cartao() {}

    public Cartao(@NotBlank String numero, Proposta proposta) {
        this.numero = numero;
        this.proposta = proposta;
    }

    @Override
    public String toString() {
        return "Cartao{" +
                "numero='" + numero + '\'' +
                '}';
    }

}
