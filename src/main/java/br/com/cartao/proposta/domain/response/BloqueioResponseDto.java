package br.com.cartao.proposta.domain.response;

import java.time.LocalDateTime;

public class BloqueioResponseDto {

    private String id;
    private LocalDateTime bloqueadoEm;
    private String sistemaResponsavel;
    private Boolean ativo;

    @Deprecated
    public BloqueioResponseDto() {
    }

    public BloqueioResponseDto(String id, LocalDateTime bloqueadoEm, String sistemaResponsavel, Boolean ativo) {
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

    public Boolean getAtivo() {
        return ativo;
    }

    @Override
    public String toString() {
        return "BloqueioResponseDto{" +
                "id='" + id + '\'' +
                ", bloqueadoEm=" + bloqueadoEm +
                ", sistemaResponsavel='" + sistemaResponsavel + '\'' +
                ", ativo=" + ativo +
                '}';
    }
}
