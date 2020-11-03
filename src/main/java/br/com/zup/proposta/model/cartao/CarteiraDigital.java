package br.com.zup.proposta.model.cartao;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import br.com.zup.proposta.controllers.apiResponses.cartao.CarteiraDigitalResponse;

@Entity
public class CarteiraDigital {

    @Id
    private String id;
    @NotNull
    private String email;
    @NotNull
    private LocalDateTime associadaEm;
    @NotNull
    private String emissor;
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

    public String getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public LocalDateTime getAssociadaEm() {
        return this.associadaEm;
    }

    public String getEmissor() {
        return this.emissor;
    }

}
