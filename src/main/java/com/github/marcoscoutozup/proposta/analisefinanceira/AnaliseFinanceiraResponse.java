package com.github.marcoscoutozup.proposta.analisefinanceira;

import com.github.marcoscoutozup.proposta.proposta.enums.ResultadoStatusProposta;
import com.github.marcoscoutozup.proposta.proposta.enums.StatusDaProposta;

import java.util.UUID;

public class AnaliseFinanceiraResponse {

    private String documento;
    private String nome;
                //1
    private ResultadoStatusProposta resultadoSolicitacao;
    private UUID idProposta;

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

    public ResultadoStatusProposta getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }

    public void setResultadoSolicitacao(ResultadoStatusProposta resultadoSolicitacao) {
        this.resultadoSolicitacao = resultadoSolicitacao;
    }

    public UUID getIdProposta() {
        return idProposta;
    }

    public void setIdProposta(UUID idProposta) {
        this.idProposta = idProposta;
    }
}
