package br.com.zup.proposta.controllers.apiResponses.cartao;

import java.time.LocalDateTime;

import br.com.zup.proposta.model.cartao.Cartao;
import br.com.zup.proposta.model.cartao.CarteiraDigital;

public class CarteiraDigitalResponse {
    
    private String id;
    private String email;
    private LocalDateTime associadaEm;
    private String emissor;

    public CarteiraDigitalResponse(String id, String email, LocalDateTime associadaEm, String emissor) {
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

    public String getEmissor() {
        return this.emissor;
    }

    public CarteiraDigital toCarteiraDigital(Cartao cartao) {
        return new CarteiraDigital(this, cartao);
    }
}
