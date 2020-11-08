package dev.arielalvesdutra.proposta.services.dtos;

import dev.arielalvesdutra.proposta.entities.AvisoViagem;

import java.time.LocalDate;

public class CadastrarAvisoViagemDTO {

    private String ip;
    private String userAgent;
    private LocalDate termino;
    private String destino;

    public CadastrarAvisoViagemDTO() {
    }

    public String getIp() {
        return ip;
    }

    public CadastrarAvisoViagemDTO setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public CadastrarAvisoViagemDTO setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    public LocalDate getTermino() {
        return termino;
    }

    public CadastrarAvisoViagemDTO setTermino(LocalDate termino) {
        this.termino = termino;
        return this;
    }

    public String getDestino() {
        return destino;
    }

    public CadastrarAvisoViagemDTO setDestino(String destino) {
        this.destino = destino;
        return this;
    }

    @Override
    public String toString() {
        return "CadastrarAvisoViagemDTO{" +
                "id='" + ip + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", termino=" + termino +
                ", destino='" + destino + '\'' +
                '}';
    }

    public AvisoViagem paraEntidade() {
        return new AvisoViagem()
                .setIp(ip)
                .setTermino(termino)
                .setUserAgent(userAgent)
                .setDestino(destino);
    }
}
