package br.com.zup.proposta.model;

import br.com.zup.proposta.enums.EstadoCartao;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
public class Cartao {

    @Id
    @GeneratedValue(generator = "uuid4")
    private UUID id;

    @NotNull
    private UUID numeroCartao;

    @NotNull
    private LocalDateTime emitidoEm;

    @OneToMany
    private Set<Biometria> biometria;

    @OneToMany  //2
    private Set<Bloqueio> bloqueio;

    @Enumerated(EnumType.STRING)
    private EstadoCartao estadoCartao;

    @Deprecated
    public Cartao() {
    }

    public Cartao(@NotNull UUID numeroCartao, @NotNull LocalDateTime emitidoEm) {
        this.numeroCartao = numeroCartao;
        this.emitidoEm = emitidoEm;
        this.estadoCartao = EstadoCartao.DESBLOQUEADO;
    }

    public UUID getId() {
        return id;
    }

    public UUID getNumeroCartao() {
        return numeroCartao;
    }

    public void incluirBiometriaNoCartao(Biometria biometria) {
        Assert.notNull(biometria, "A biometria não pode ser nula");
        this.biometria.add(biometria);
    }

    public void incluirBloqueioDoCartao(Bloqueio bloqueio){
        Assert.notNull(bloqueio, "O bloqueio do cartão não pode ser nulo");
        this.bloqueio.add(bloqueio);
    }

    public void bloquearCartao(){
        this.estadoCartao = EstadoCartao.BLOQUEADO;
    }

    public boolean verificarSeOCartaoEstaBloqueado(){
        return estadoCartao.equals(EstadoCartao.BLOQUEADO);
    }
}
