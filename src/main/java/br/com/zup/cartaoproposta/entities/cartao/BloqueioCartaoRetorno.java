package br.com.zup.cartaoproposta.entities.cartao;

import java.time.LocalDateTime;

/**
 * Contagem de carga intrínseca da classe: 0
 */

public class BloqueioCartaoRetorno {

    private String id;
    private LocalDateTime bloqueadoEm;
    private String sistemaResponsavel;
    private boolean ativo;

    @Deprecated
    public BloqueioCartaoRetorno(){}

    public BloqueioCartaoRetorno(String id, LocalDateTime bloqueadoEm, String sistemaResponsavel, boolean ativo) {
        this.id = id;
        this.bloqueadoEm = bloqueadoEm;
        this.sistemaResponsavel = sistemaResponsavel;
        this.ativo = ativo;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBloqueadoEm(LocalDateTime bloqueadoEm) {
        this.bloqueadoEm = bloqueadoEm;
    }

    public void setSistemaResponsavel(String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
