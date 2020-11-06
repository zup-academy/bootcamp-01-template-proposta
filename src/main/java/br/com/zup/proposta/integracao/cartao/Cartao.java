package br.com.zup.proposta.integracao.cartao;

import br.com.zup.proposta.integracao.biometria.Biometria;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

    @Deprecated
    public Cartao() {
    }

    public Cartao(@NotBlank String numeroCartao, @NotNull LocalDateTime emitidoEm, @NotBlank String titular) {
        this.numeroCartao = numeroCartao;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
    }

    public void addBiometria(Biometria biometria) {
        biometrias.add(biometria);
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }
}
