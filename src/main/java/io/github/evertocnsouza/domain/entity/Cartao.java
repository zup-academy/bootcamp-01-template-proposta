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

    @Deprecated
    public Cartao(){
    }
    public Cartao(UUID numeroCartao, LocalDateTime emitidoEm) {
        this.numeroCartao = numeroCartao;
        this.emitidoEm = emitidoEm;
        this.statusCartao = statusCartao.ATIVO;
    }

    public void addBiometria(Biometria biometria) {
        Assert.notNull(biometrias, "Biometria não pode ser nula para associação cartão");
        biometrias.add(biometria);
    }

    public UUID getNumeroCartao() {
        return numeroCartao;
    }
}