package br.com.zup.bootcamp.proposta.domain.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@EnableScheduling
public class Cartao {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotNull
    private UUID idCartaoEmitido;

    @CreationTimestamp
    private LocalDateTime emitidoEm;
    @NotBlank
    private String titular;
    @OneToMany
    private Set<Bloqueio> bloqueios;
    @OneToMany
    private Set<Aviso> avisos;
    @OneToMany
    private Set<Carteira> carteiras;
    @Positive
    private BigDecimal limite;
    @OneToMany
    private Set<Biometria> biometrias;

    @Enumerated(EnumType.STRING)
    private EstadoCartao estadoCartao;

    @Deprecated
    public Cartao() {
    }

    public Cartao(@NotNull UUID idCartaoEmitido, LocalDateTime emitidoEm, @NotBlank String titular, @NotBlank BigDecimal limite) {
        this.idCartaoEmitido = idCartaoEmitido;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.limite = limite;
        this.estadoCartao = EstadoCartao.BLOQUEADO;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public BigDecimal getLimite() {
        return limite;
    }

    public void setLimite(BigDecimal limite) {
        this.limite = limite;
    }

    public UUID getIdCartaoEmitido() {
        return idCartaoEmitido;
    }

    public String getId() {
        return id;
    }

    public void adicionarBiometriaNoCartao(Biometria biometria) {
        Assert.notNull(biometria, "A biometria não pode ser nula para associação do cartão");
        biometrias.add(biometria);
    }

    public void adicionarBloqueioDoCartao(Bloqueio bloqueio) {
        Assert.notNull(bloqueio, "O bloqueio do cartão não pode ser nulo");
        bloqueios.add(bloqueio);
    }

    public void adicionarAvisoDeViagem(Aviso aviso) {
        Assert.notNull(aviso, "O aviso não pode ser nulo para associação com o cartão");
        avisos.add(aviso);
    }

    public void adicionarCarteira(Carteira carteira) {
        Assert.notNull(carteira, "A carteira não pode ser nula para associação com o cartão");
        carteiras.add(carteira);
    }
}
