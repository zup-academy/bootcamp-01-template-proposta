package br.com.zup.proposta.integracao.analiseproposta;

public class ResultadoAnaliseResponse {
    private String documento;
    private String nome;
    private RespostaStatusAvaliacao resultadoSolicitacao;
    private String idProposta;

    public ResultadoAnaliseResponse(String documento, String nome,
                                    RespostaStatusAvaliacao resultadoSolicitacao, String idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.resultadoSolicitacao = resultadoSolicitacao;
        this.idProposta = idProposta;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public RespostaStatusAvaliacao getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }

    public void setResultadoSolicitacao(RespostaStatusAvaliacao resultadoSolicitacao) {
        this.resultadoSolicitacao = resultadoSolicitacao;
    }

    public String getIdProposta() {
        return idProposta;
    }

    public void setIdProposta(String idProposta) {
        this.idProposta = idProposta;
    }

    public StatusAvaliacaoProposta retornaStatus() {
        return this.resultadoSolicitacao.getStatusAvaliacao();
    }
}
