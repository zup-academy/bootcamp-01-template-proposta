package br.com.zup.proposta.integracao.cartao;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Cartao {
    @Id
    private @NotBlank String id;
    private @NotNull LocalDateTime emitidoEm;
    private @NotBlank String titular;

    @Deprecated
    public Cartao() {
    }

    public Cartao(@NotBlank String id, @NotNull LocalDateTime emitidoEm, @NotBlank String titular) {
        this.id = id;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
    }
}
