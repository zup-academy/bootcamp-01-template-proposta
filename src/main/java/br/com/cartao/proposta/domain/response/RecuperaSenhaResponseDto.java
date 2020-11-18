package br.com.cartao.proposta.domain.response;

import br.com.cartao.proposta.domain.model.Cartao;
import br.com.cartao.proposta.domain.model.RecuperaSenha;

import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

public class RecuperaSenhaResponseDto {

    private final String id;
    private final LocalDateTime instante;
    private final String ipAddressCliente;
    private final String userAgent;
    private final Cartao cartao;

    public RecuperaSenhaResponseDto(RecuperaSenha recuperaSenha) {
        this.id = recuperaSenha.getId();
        this.instante = recuperaSenha.getInstante();
        this.ipAddressCliente = recuperaSenha.getIpAddressCliente();
        this.userAgent = recuperaSenha.getUserAgent();
        this.cartao = recuperaSenha.getCartao();
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getInstante() {
        return instante;
    }

    public String getIpAddressCliente() {
        return ipAddressCliente;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public Cartao getCartao() {
        return cartao;
    }
}
