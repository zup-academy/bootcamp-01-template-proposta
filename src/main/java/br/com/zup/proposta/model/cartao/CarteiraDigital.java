package br.com.zup.proposta.model.cartao;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import br.com.zup.proposta.controllers.apiResponses.cartao.CarteiraDigitalResponse;
import br.com.zup.proposta.model.enums.Carteiras;

@Entity
public class CarteiraDigital {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotNull
    private String email;
    @NotNull
    private LocalDateTime associadaEm;
    @NotNull
    private Carteiras emissor;
    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public CarteiraDigital(){}

    public CarteiraDigital(CarteiraDigitalResponse response, Cartao cartao) {
        this.id = response.getId();
        this.email = response.getEmail();
        this.associadaEm = response.getAssociadaEm();
        this.emissor = response.getEmissor();
        this.cartao = cartao;
    }

    public CarteiraDigital(String email, Carteiras emissor, Cartao cartao) {
        this.email = email;
        this.associadaEm = LocalDateTime.now();
        this.emissor = emissor;
        this.cartao = cartao;
    }

    public String getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public LocalDateTime getAssociadaEm() {
        return this.associadaEm;
    }

    public Carteiras getEmissor() {
        return this.emissor;
    }

}
