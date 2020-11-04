package br.com.zup.cartaoproposta.entities.cartao.bloqueio;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

/**
 * Contagem de carga intrínseca da classe: 0
 */

@Embeddable
public class BloqueioCartao {

    private String id;
    private LocalDateTime bloqueadoEm;
    private String sistemaResponsavel;
    private boolean ativo;

    @Deprecated
    public BloqueioCartao(){}

    public BloqueioCartao(String id, LocalDateTime bloqueadoEm, String sistemaResponsavel, boolean ativo) {
        this.id = id;
        this.bloqueadoEm = bloqueadoEm;
        this.sistemaResponsavel = sistemaResponsavel;
        this.ativo = ativo;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getBloqueadoEm() {
        return bloqueadoEm;
    }

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }

    public boolean isAtivo() {
        return ativo;
    }
}
