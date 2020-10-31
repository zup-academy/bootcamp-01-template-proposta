package br.com.proposta.entidades;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Cartao {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotNull
    private OffsetDateTime emitidoEm;

    @NotBlank
    private String titular;

    @NotBlank
    private String numero;

    @OneToMany(mappedBy = "cartao")
    private Set<Bloqueio> bloqueios = new HashSet<>();

    @OneToMany(mappedBy = "cartao")
    private Set<Aviso> avisos = new HashSet<>();

    @OneToMany(mappedBy = "cartao")
    private Set<Carteira> carteiras = new HashSet<>();

    @OneToOne
    private Proposta proposta;

    @Deprecated
    public Cartao(){}

    public Cartao(String numero, String titular, Proposta proposta) {
        this.numero = numero;
        this.titular = titular;
        this.proposta = proposta;
        this.emitidoEm = OffsetDateTime.now();
    }

    public String getNumero() {
        return numero;
    }
}
