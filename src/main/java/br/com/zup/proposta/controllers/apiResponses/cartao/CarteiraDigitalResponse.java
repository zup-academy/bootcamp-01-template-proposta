package br.com.zup.proposta.controllers.apiResponses.cartao;

import java.time.LocalDateTime;

import br.com.zup.proposta.model.cartao.Cartao;
import br.com.zup.proposta.model.cartao.CarteiraDigital;
import br.com.zup.proposta.model.enums.Carteiras;

public class CarteiraDigitalResponse {
    
    private String id;
    private String email;
    private LocalDateTime associadaEm;
    private Carteiras emissor;

    public CarteiraDigitalResponse(String id, String email, LocalDateTime associadaEm, Carteiras emissor) {
        this.id = id;
        this.email = email;
        this.associadaEm = associadaEm;
        this.emissor = emissor;
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

    public CarteiraDigital toCarteiraDigital(Cartao cartao) {
        return new CarteiraDigital(this, cartao);
    }
}
