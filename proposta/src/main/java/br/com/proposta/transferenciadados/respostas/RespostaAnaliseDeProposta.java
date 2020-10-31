package br.com.proposta.transferenciadados.respostas;

import br.com.proposta.entidades.Enums.RespostaStatusAvaliacao;
import br.com.proposta.entidades.Enums.StatusAvaliacaoProposta;

public class RespostaAnaliseDeProposta {


    private String documento;

    private String nome;

    private RespostaStatusAvaliacao resultadoSolicitacao;

    private String idProposta;


    @Deprecated
    public RespostaAnaliseDeProposta(){}

    public RespostaAnaliseDeProposta(String documento, String nome, RespostaStatusAvaliacao resultadoSolicitacao, String idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.resultadoSolicitacao = resultadoSolicitacao;
        this.idProposta = idProposta;
    }

    public StatusAvaliacaoProposta retornaStatus(){

        return this.resultadoSolicitacao.getStatusAvaliacao();

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
