package br.com.zup.proposta.cartao;

import br.com.zup.proposta.biometria.Biometria;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Cartao {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private @NotBlank String numeroCartao;
    private @NotNull LocalDateTime emitidoEm;
    private @NotBlank String titular;
    private @OneToMany Set<Biometria> biometrias = new HashSet<>();
    private @Enumerated(EnumType.STRING) StatusCartao status;

    @Deprecated
    public Cartao() {
    }

    public Cartao(@NotBlank String numeroCartao, @NotNull LocalDateTime emitidoEm, @NotBlank String titular) {
        this.numeroCartao = numeroCartao;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.status = StatusCartao.ATIVO;
    }

    public void addBiometria(Biometria biometria) {
        biometrias.add(biometria);
    }

    public String getId() {
        return id;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setStatus(StatusCartao status) {
        this.status = status;
    }

    public void bloquearCartao() {
        this.status = StatusCartao.BLOQUEADO;
    }
}
