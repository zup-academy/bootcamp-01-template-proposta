package br.com.itau.cartaobrancoproposta.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class AvisoViagem {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotNull
    @Future
    @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate validoAte;
    private String destino;
    @NotBlank
    private String ipCliente;
    @NotBlank
    private String userAgent;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime instanteAviso = LocalDateTime.now();
    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public AvisoViagem() {
    }

    public AvisoViagem(LocalDate validoAte, String destino, @NotBlank String ipCliente, @NotBlank String userAgent) {
        this.validoAte = validoAte;
        this.destino = destino;
        this.ipCliente = ipCliente;
        this.userAgent = userAgent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }

    public void setValidoAte(LocalDate validoAte) {
        this.validoAte = validoAte;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
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

    public LocalDateTime getInstanteAviso() {
        return instanteAviso;
    }

    public void setInstanteAviso(LocalDateTime instanteAviso) {
        this.instanteAviso = instanteAviso;
    }
}
