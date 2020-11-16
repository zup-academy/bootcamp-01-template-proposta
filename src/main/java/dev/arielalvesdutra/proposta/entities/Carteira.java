package dev.arielalvesdutra.proposta.entities;

import dev.arielalvesdutra.proposta.entities.enums.CarteiraTipo;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

/**
 * Entidade que representa uma Carteira Digital.
 */
@Table(name = "carteira")
@Entity
public class Carteira {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @Email(message = "{email.formato_invalido}")
    @NotBlank(message = "{email.obrigatorio}")
    private String email;
    @NotNull(message = "Tipo da carteira é obrigatório!")
    @Enumerated(EnumType.STRING)
    private CarteiraTipo tipo;
    @NotBlank(message = "ID do legado é obrigatório!")
    private String legadoId;
    private OffsetDateTime cadastradoEm = OffsetDateTime.now();

    @NotNull(message = "{cartao.obrigatorio}")
    @ManyToOne
    private Cartao cartao;

    public Carteira() {
    }

    public String getId() {
        return id;
    }

    public Carteira setId(String id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Carteira setEmail(String email) {
        this.email = email;
        return this;
    }

    public CarteiraTipo getTipo() {
        return tipo;
    }

    public Carteira setTipo(CarteiraTipo tipo) {
        this.tipo = tipo;
        return this;
    }

    public String getLegadoId() {
        return legadoId;
    }

    public Carteira setLegadoId(String legadoId) {
        this.legadoId = legadoId;
        return this;
    }

    public OffsetDateTime getCadastradoEm() {
        return cadastradoEm;
    }

    public Carteira setCadastradoEm(OffsetDateTime cadastradoEm) {
        this.cadastradoEm = cadastradoEm;
        return this;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public Carteira setCartao(Cartao cartao) {
        this.cartao = cartao;
        return this;
    }

    @Override
    public String toString() {
        return "Carteira{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", tipo=" + tipo +
                '}';
    }
}
