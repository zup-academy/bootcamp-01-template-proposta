package dev.arielalvesdutra.proposta.services.dtos;

import dev.arielalvesdutra.proposta.entities.CartaoBloqueio;

/**
 * DTO para auxiliar o cadastro de bloqueio de cartão.
 */
public class CadastrarBloqueioDTO {

    /**
     * E-mail extraido do token recebido do usuário autenticado na requisição.
     */
    private String email;
    private String ip;
    private String userAgent;

    public CadastrarBloqueioDTO() {
    }

    public String getEmail() {
        return email;
    }

    public CadastrarBloqueioDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public CadastrarBloqueioDTO setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public CadastrarBloqueioDTO setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    @Override
    public String toString() {
        return "CadastrarBloqueioDTO{" +
                "email='" + email + '\'' +
                ", ip='" + ip + '\'' +
                ", userAgent='" + userAgent + '\'' +
                '}';
    }

    public CartaoBloqueio paraEntidade() {
        return new CartaoBloqueio()
                .setIp(ip)
                .setUserAgent(userAgent);
    }
}
