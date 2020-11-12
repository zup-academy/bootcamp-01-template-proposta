package br.com.zup.bootcamp.proposta.domain.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
public class RecuperarSenha {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotBlank
    private String ip;

    @NotBlank
    private String sistemaResponsavel;

    @CreationTimestamp
    private LocalDateTime SolicitadoEm;

    @Valid @ManyToOne
    private Cartao cartao;

    @Deprecated
    public RecuperarSenha() {
    }

    public RecuperarSenha(String ip, String sistemaResponsavel, Cartao cartao) {
        this.ip = ip;
        this.sistemaResponsavel = sistemaResponsavel;
        this.cartao = cartao;
    }

    public String getId() {
        return id;
    }
}
