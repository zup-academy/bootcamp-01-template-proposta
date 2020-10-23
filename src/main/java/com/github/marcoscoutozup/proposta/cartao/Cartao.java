package com.github.marcoscoutozup.proposta.cartao;

import com.github.marcoscoutozup.proposta.biometria.Biometria;
import com.github.marcoscoutozup.proposta.bloqueio.Bloqueio;
import com.github.marcoscoutozup.proposta.bloqueio.enums.EstadoCartao;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
public class Cartao {

    @Id
    private UUID id;

    @NotNull
    private LocalDateTime emitidoEm;

    @OneToMany
    private Set<Biometria> biometrias;

    @OneToMany
    private Set<Bloqueio> bloqueios;

    @Enumerated(EnumType.STRING)
    private EstadoCartao estadoCartao;

    @Deprecated
    public Cartao() {
    }

    public Cartao(UUID id, LocalDateTime emitidoEm) {
        this.id = id;
        this.emitidoEm = emitidoEm;
        this.estadoCartao = EstadoCartao.DESBLOQUEADO;
    }

    public void incluirBiometriaNoCartao(Biometria biometria) {
        Assert.notNull(biometria, "A biometria não pode ser nula para associação do cartão");
        biometrias.add(biometria);
    }

    public void incluirBloqueioDoCartao(Bloqueio bloqueio){
        Assert.notNull(bloqueio, "O bloqueio do cartão não pode ser nulo");
        bloqueios.add(bloqueio);
    }

    public void bloquearCartao(){
        this.estadoCartao = EstadoCartao.BLOQUEADO;
    }

    public boolean verificarSeOCartaoEstaoBloqueado(){
        return estadoCartao.equals(EstadoCartao.BLOQUEADO);
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Cartao{" +
                "id=" + id +
                ", emitidoEm=" + emitidoEm +
                '}';
    }
}
