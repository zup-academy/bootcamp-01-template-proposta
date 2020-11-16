package dev.arielalvesdutra.proposta.http_clients.dtos;

/**
 * DTO do corpo da requisição (request body) para solicitar o bloqueio
 * de um cartão.
 */
public class SolicitacaoBloqueioDTO {

    private String sistemaResponsavel;

    public SolicitacaoBloqueioDTO() {
    }

    public SolicitacaoBloqueioDTO(String sistemaResponsavel) {
        setSistemaResponsavel(sistemaResponsavel);
    }

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }

    public SolicitacaoBloqueioDTO setSistemaResponsavel(String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
        return this;
    }
}
