package io.github.evertocnsouza.rest.dto.response;

import io.github.evertocnsouza.domain.enums.StatusAvaliacaoProposta;
import io.github.evertocnsouza.domain.enums.StatusAvaliacaoResposta;

public class ResultadoAnaliseProposta {

    private String documento;

    private String nome;

    private StatusAvaliacaoResposta resultadoSolicitacao;

    private String idProposta;


    public ResultadoAnaliseProposta(String documento, String nome,
                                    StatusAvaliacaoResposta resultadoSolicitacao, String idProposta) {
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

    public StatusAvaliacaoResposta getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }

    public void setResultadoSolicitacao(StatusAvaliacaoResposta resultadoSolicitacao) {
        this.resultadoSolicitacao = resultadoSolicitacao;
    }

    public String getIdProposta() {
        return idProposta;
    }

    public void setIdProposta(String idProposta) {
        this.idProposta = idProposta;
    }
}

