package dev.arielalvesdutra.proposta.services.dtos;

import dev.arielalvesdutra.proposta.entities.SolicitacaoRecuperacaoSenha;

public class CadastrarSolicitacaoRecuperacaoSenhaDTO {

    private String ip;
    private String userAgent;

    public CadastrarSolicitacaoRecuperacaoSenhaDTO() {
    }

    public String getIp() {
        return ip;
    }

    public CadastrarSolicitacaoRecuperacaoSenhaDTO setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public CadastrarSolicitacaoRecuperacaoSenhaDTO setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    @Override
    public String toString() {
        return "CadastrarSolicitacaoRecuperacaoSenhaDTO{" +
                "ip='" + ip + '\'' +
                ", userAgent='" + userAgent + '\'' +
                '}';
    }

    public SolicitacaoRecuperacaoSenha paraEntidade() {

        return new SolicitacaoRecuperacaoSenha()
                .setIp(ip)
                .setUserAgent(userAgent);
    }
}
