package dev.arielalvesdutra.proposta.entities;

import dev.arielalvesdutra.proposta.entities.interfaces.Timestamper;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.time.temporal.Temporal;
import java.util.Objects;

@Table(name = "biometria")
@Entity
public class Biometria implements Timestamper {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    /**
     * Valor da biometria deve ser uma String em Base64 que
     * represente a imagem de uma Biometria.
     */
    @NotBlank(message = "Valor da biometria é obrigatório!")
    @Column(columnDefinition = "TEXT")
    private String valor;
    private OffsetDateTime cadastradoEm = OffsetDateTime.now();
    private OffsetDateTime atualizadoEm;

    @NotNull(message = "{cartao.obrigatorio}")
    @ManyToOne
    private Cartao cartao;

    public Biometria() {
    }

    public String getId() {
        return id;
    }

    public Biometria setId(String id) {
        this.id = id;
        return this;
    }

    public String getValor() {
        return valor;
    }

    public Biometria setValor(String valor) {
        this.valor = valor;
        return this;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public Biometria setCartao(Cartao cartao) {
        this.cartao = cartao;
        return this;
    }

    @Override
    public Temporal getCadastradoEm() {
        return cadastradoEm;
    }

    @Override
    public Temporal getAtualizadoEm() {
        return atualizadoEm;
    }

    @Override
    public String toString() {
        return "Biometria{" +
                "id='" + id + '\'' +
                ", value='" + valor + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Biometria biometria = (Biometria) o;
        return Objects.equals(id, biometria.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @PreUpdate
    private void preUpdate() {
        atualizadoEm = OffsetDateTime.now();
    }
}
