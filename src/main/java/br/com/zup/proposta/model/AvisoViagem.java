package br.com.zup.proposta.model;

import br.com.zup.proposta.model.enums.StatusAvisoViagem;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class AvisoViagem {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    @NotNull
    @Valid
    @ManyToOne
    private Cartao cartao;
    private @NotBlank String destinoViagem;
    @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
    private @Future @NotNull LocalDate dataTerminoViagem;
    private @NotBlank String userAgent;
    private @NotBlank String ipClient;
    private LocalDateTime instanteAviso;
    @Enumerated(EnumType.STRING)
    private StatusAvisoViagem statusAvisoViagem;

    @Deprecated
    public AvisoViagem(){}

    public AvisoViagem(@NotNull @Valid Cartao cartao, @NotBlank String destinoViagem,
                       @Future @NotNull LocalDate dataTerminoViagem, @NotBlank String userAgent,
                       @NotBlank String ipClient) {
        this.cartao = cartao;
        this.destinoViagem = destinoViagem;
        this.dataTerminoViagem = dataTerminoViagem;
        this.userAgent = userAgent;
        this.ipClient = ipClient;
        this.instanteAviso = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void atualizarStatusViagem() {
        this.statusAvisoViagem = StatusAvisoViagem.CRIADO;
    }

    @Override
    public String toString() {
        return "AvisoViagem{" +
                ", destinoViagem='" + destinoViagem + '\'' +
                ", dataTerminoViagem=" + dataTerminoViagem +
                '}';
    }


}
