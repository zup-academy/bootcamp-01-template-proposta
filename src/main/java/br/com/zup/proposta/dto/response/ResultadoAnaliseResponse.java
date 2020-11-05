package br.com.zup.proposta.dto.response;

import br.com.zup.proposta.enums.RespostaStatusAvaliacao;
import br.com.zup.proposta.enums.StatusAvaliacaoProposta;

public class ResultadoAnaliseResponse {

    private String documento;

    private String nome;

    private RespostaStatusAvaliacao resultadoSolicitacao;

    private String idProposta;


    @Deprecated
    public ResultadoAnaliseResponse(){}

    public ResultadoAnaliseResponse(String documento, String nome, RespostaStatusAvaliacao resultadoSolicitacao, String idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.resultadoSolicitacao = resultadoSolicitacao;
        this.idProposta = idProposta;
    }

    public StatusAvaliacaoProposta retornaStatus(){

        return this.resultadoSolicitacao.getStatusProposta();

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
}
