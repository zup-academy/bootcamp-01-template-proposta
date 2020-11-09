package br.com.cartao.proposta.domain.response;

import br.com.cartao.proposta.domain.model.Cartao;
import br.com.cartao.proposta.domain.model.Bloqueio;

import java.time.LocalDateTime;

public class BloqueioCartaoResponseDto {


    private String id;
    private String ipAddressCliente;
    private String userAgent;
    private LocalDateTime instanteBloqueio;
    private Cartao cartao;

    public BloqueioCartaoResponseDto(Bloqueio bloqueio) {
        this.id = bloqueio.getId();
        this.ipAddressCliente = bloqueio.getIpAddressCliente();
        this.userAgent = bloqueio.getUserAgent();
        this.instanteBloqueio = bloqueio.getInstanteBloqueio();
        this.cartao = bloqueio.getCartao();
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "BloqueioCartaoResponseDto{" +
                "id='" + id + '\'' +
                ", ipAddressCliente='" + ipAddressCliente + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", instanteBloqueio=" + instanteBloqueio +
                ", cartao=" + cartao +
                '}';
    }
}
