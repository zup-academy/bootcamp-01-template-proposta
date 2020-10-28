package com.proposta.criacaoproposta;

public class ResultadoAnaliseResponse {

    private String documento;

    private String nome;

    private ResultadoSolicitacao resultadoSolicitacao;

    private String idProposta;

    public ResultadoAnaliseResponse(String documento, String nome, ResultadoSolicitacao resultadoSolicitacao, String idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.resultadoSolicitacao = resultadoSolicitacao;
        this.idProposta = idProposta;
    }

    public enum ResultadoSolicitacao {
        COM_RESTRICAO, SEM_RESTRICAO
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public ResultadoSolicitacao getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }

    public String getIdProposta() {
        return idProposta;
    }
}
