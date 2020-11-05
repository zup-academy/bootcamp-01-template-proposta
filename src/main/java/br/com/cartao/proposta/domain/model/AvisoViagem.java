package br.com.cartao.proposta.domain.model;

import br.com.cartao.proposta.domain.enums.EstadoAvisoViagem;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "avisoViagem")
public class AvisoViagem {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotNull
    private String destinoViagem;
    @Future
    private LocalDate terminaEm;
    private String ipAddress;
    private String userAgent;
    private LocalDateTime instante;
    private Boolean avisoSistemaLegado;
    @ManyToOne
    private Cartao cartao;

    public AvisoViagem() {
    }

    public AvisoViagem(@NotBlank String destinoViagem, @NotNull @Future LocalDate terminaEm, String ipAddress, String userAgent, Cartao cartao) {

        this.destinoViagem = destinoViagem;
        this.terminaEm = terminaEm;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.instante = LocalDateTime.now();
        this.avisoSistemaLegado = Boolean.FALSE;
        this.cartao = cartao;
    }

    public String getId() {
        return id;
    }

    public String getDestinoViagem() {
        return destinoViagem;
    }

    public LocalDate getTerminaEm() {
        return terminaEm;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public LocalDateTime getInstante() {
        return instante;
    }

    public Boolean getAvisoSistemaLegado() {
        return avisoSistemaLegado;
    }

    public Cartao getCartao() {
        return cartao;
    }

    @Override
    public String toString() {
        return "AvisoViagem{" +
                "destinoViagem='" + destinoViagem + '\'' +
                ", terminaEm=" + terminaEm +
                ", ipAddress='" + ipAddress + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", instante=" + instante +
                ", cartao=" + cartao +
                '}';
    }

    public void estadoAvisoComFalha(){
        this.avisoSistemaLegado = Boolean.FALSE;
    }

    public void estadoAvisoComSucesso(){
        this.avisoSistemaLegado = Boolean.TRUE;
    }
}
