package io.github.evertocnsouza.domain.entity;

import io.github.evertocnsouza.domain.enums.StatusCartao;
import org.springframework.util.Assert;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private UUID numeroCartao;

    @NotNull
    private LocalDateTime emitidoEm;

    @Enumerated(EnumType.STRING)
    private StatusCartao statusCartao;

    @OneToMany
    private Set<Biometria> biometrias = new HashSet<>();

    @OneToMany
    private Set<Bloqueio> bloqueios = new HashSet<>();

    @Deprecated
    public Cartao(){
    }
    public Cartao(UUID numeroCartao, LocalDateTime emitidoEm) {
        this.numeroCartao = numeroCartao;
        this.emitidoEm = emitidoEm;
        this.statusCartao = statusCartao.ATIVO;
    }

    public void addBiometria(Biometria biometria) {
        Assert.notNull(biometrias, "Biometria não pode ser nula para associar ao cartão");
        biometrias.add(biometria);
    }

    public boolean verificarCartaoBloqueado() {
        return statusCartao.equals(statusCartao.BLOQUEADO);
    }

    public void bloquearCartao() {
        this.statusCartao= statusCartao.BLOQUEADO;
    }

    public void incluirBloqueioDoCartao(Bloqueio bloqueio){
        bloqueios.add(bloqueio);
    }

    public Long getId() {
        return id;
    }

    public UUID getNumeroCartao() {
        return numeroCartao;
    }
}