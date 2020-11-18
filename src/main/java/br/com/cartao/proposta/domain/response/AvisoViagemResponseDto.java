package br.com.cartao.proposta.domain.response;

import br.com.cartao.proposta.domain.model.AvisoViagem;
import br.com.cartao.proposta.domain.model.Cartao;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AvisoViagemResponseDto {

    private final String id;
    private final String destinoViagem;
    private final LocalDate terminaEm;
    private final String ipAddress;
    private final String userAgent;
    private final LocalDateTime instante;
    private final Cartao cartao;

    public AvisoViagemResponseDto(AvisoViagem avisoViagem) {
        this.id = avisoViagem.getId();
        this.destinoViagem = avisoViagem.getDestinoViagem();
        this.terminaEm = avisoViagem.getTerminaEm();
        this.ipAddress = avisoViagem.getIpAddress();
        this.userAgent  = avisoViagem.getUserAgent();
        this.instante = avisoViagem.getInstante();
        this.cartao = avisoViagem.getCartao();
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "AvisoViagemResponseDto{" +
                "id='" + id + '\'' +
                ", destinoViagem='" + destinoViagem + '\'' +
                ", terminaEm=" + terminaEm +
                ", ipAddress='" + ipAddress + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", instante=" + instante +
                ", cartao=" + cartao +
                '}';
    }
}
