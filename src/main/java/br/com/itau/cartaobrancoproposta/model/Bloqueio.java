package br.com.itau.cartaobrancoproposta.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Bloqueio {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotBlank
    private String ipCliente;
    @NotBlank
    private String userAgent;
    @NotNull
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime localDateTime = LocalDateTime.now();
    @NotNull
    @Enumerated(EnumType.STRING)
    private EstadoBloqueio estadoBloqueio;
    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public Bloqueio() {
    }

    public Bloqueio(@NotBlank String ipCliente, @NotBlank String userAgent, @NotNull EstadoBloqueio estadoBloqueio) {
        this.ipCliente = ipCliente;
        this.userAgent = userAgent;
        this.estadoBloqueio = estadoBloqueio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIpCliente() {
        return ipCliente;
    }

    public void setIpCliente(String ipCliente) {
        this.ipCliente = ipCliente;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public EstadoBloqueio getEstadoBloqueio() {
        return estadoBloqueio;
    }

    public void setEstadoBloqueio(EstadoBloqueio estadoBloqueio) {
        this.estadoBloqueio = estadoBloqueio;
    }

    public void statusBloqueado() {
        this.estadoBloqueio = EstadoBloqueio.BLOQUEADO;
    }
}
