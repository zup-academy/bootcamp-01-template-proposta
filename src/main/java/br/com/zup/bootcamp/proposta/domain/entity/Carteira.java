package br.com.zup.bootcamp.proposta.domain.entity;

import br.com.zup.bootcamp.proposta.domain.service.enums.EmpresaAssociada;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Carteira {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Email @NotBlank
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EmpresaAssociada carteira;

    @CreationTimestamp
    private LocalDateTime solicitadoEm;

    @ManyToOne
    @Valid @NotNull
    private Cartao cartao;

    @Deprecated
    public Carteira() {
    }

    public Carteira(@Email @NotBlank String email, @NotNull EmpresaAssociada carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public Carteira(@Email @NotBlank String email, @NotNull EmpresaAssociada carteira, @Valid @NotNull Cartao cartao) {
        this.email = email;
        this.carteira = carteira;
        this.cartao = cartao;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public EmpresaAssociada getCarteira() {
        return carteira;
    }

    @Override
    public String toString() {
        return "Carteira{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", empresa=" + carteira +
                ", solicitadoEm=" + solicitadoEm +
                '}';
    }
}
