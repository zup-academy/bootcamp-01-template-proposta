package br.com.zup.cartaoproposta.entities.cartao;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Contagem de carga intrínseca da classe: 1
 */

public class CartaoRetorno {

    @NotNull
    private String idCartao;
    @NotNull
    private LocalDateTime emitidoEm;
    @NotNull
    private String titular;

    //1
    public CartaoRetorno(Cartao cartao) {
        this.idCartao = cartao.getId();
        this.emitidoEm = cartao.getEmitidoEm();
        this.titular = cartao.getTitular();
    }

    public String getIdCartao() {
        return idCartao;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public String getTitular() {
        return titular;
    }
}
