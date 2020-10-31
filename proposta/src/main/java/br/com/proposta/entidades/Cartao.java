package br.com.proposta.entidades;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Cartao {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private OffsetDateTime emitidoEm;

    private String titular;

    @OneToMany(mappedBy = "cartao")
    private Set<Bloqueio> bloqueios = new HashSet<>();

    @OneToMany(mappedBy = "cartao")
    private Set<Aviso> avisos = new HashSet<>();

    @OneToMany(mappedBy = "cartao")
    private Set<Carteira> carteiras = new HashSet<>();

    @OneToOne
    private Proposta proposta;

    public Cartao(String titular, Proposta proposta) {
        this.titular = titular;
        this.proposta = proposta;
        this.emitidoEm = OffsetDateTime.now();
    }
}
