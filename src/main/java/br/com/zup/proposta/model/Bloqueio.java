package br.com.zup.proposta.model;

import br.com.zup.proposta.model.enums.StatusCartao;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Bloqueio {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @ManyToOne
    @NotNull
    @Valid
    private Cartao cartao;
    @NotBlank
    private String userAgent;
    @NotBlank
    private String ipCliente;
    private LocalDateTime instanteBloqueio;

    @Deprecated
    public Bloqueio(){}

    public Bloqueio(@NotNull @Valid Cartao cartao, @NotBlank String userAgent,
                    @NotBlank String ipCliente) {
        this.cartao = cartao;
        this.userAgent = userAgent;
        this.ipCliente = ipCliente;
        this.instanteBloqueio = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void bloquearCartao(){
    }

    public String numeroCartao(){
        return this.cartao.getNumero();
    }

    @Override
    public String toString() {
        return "Bloqueio{" +
                "userAgent='" + userAgent + '\'' +
                ", ipClient='" + ipCliente + '\'' +
                '}';
    }

}
