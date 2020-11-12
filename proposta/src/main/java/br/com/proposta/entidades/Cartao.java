package br.com.proposta.entidades;

import br.com.proposta.entidades.enums.StatusBloqueio;
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
    private OffsetDateTime emitidoEm = OffsetDateTime.now();

    @NotBlank
    private String titular;

    @NotBlank
    @Column(unique = true)
    private String numero;

    @OneToMany(mappedBy = "cartao")
    private Set<Bloqueio> bloqueios = new HashSet<>();

    @OneToMany(mappedBy = "cartao")
    private Set<Aviso> avisos = new HashSet<>();

    @OneToMany(mappedBy = "cartao")
    private Set<Carteira> carteiras = new HashSet<>();

    @OneToMany(mappedBy = "cartao")
    private Set<Biometria> biometrias = new HashSet<>();

    @OneToOne
    private Proposta proposta;

    @Enumerated(EnumType.STRING)
    private StatusBloqueio status = StatusBloqueio.DESBLOQUEADO;

    @Deprecated
    public Cartao(){}

    public Cartao(String numero, String titular, Proposta proposta) {
        this.numero = numero;
        this.titular = titular;
        this.proposta = proposta;
    }

    public void adicionarBiometria(Biometria biometria){
        this.biometrias.add(biometria);
    }

    public String getNumero() {
        return numero;
    }

    public void setStatus(StatusBloqueio status) {
        this.status = status;
    }

    public String getTitular() {
        return titular;
    }
}
