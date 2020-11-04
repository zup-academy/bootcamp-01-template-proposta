package br.com.itau.cartaobrancoproposta.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Embeddable;

@Embeddable
public class CarteiraDigital {

    @JsonProperty(value = "id")
    private String idCarteiraDigital;
    private String email;
    private String associadaEm;
    private String emissor;

    public CarteiraDigital(String idCarteiraDigital, String email, String associadaEm, String emissor) {
        this.idCarteiraDigital = idCarteiraDigital;
        this.email = email;
        this.associadaEm = associadaEm;
        this.emissor = emissor;
    }

    public String getIdCarteiraDigital() {
        return idCarteiraDigital;
    }

    public void setIdCarteiraDigital(String idCarteiraDigital) {
        this.idCarteiraDigital = idCarteiraDigital;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAssociadaEm() {
        return associadaEm;
    }

    public void setAssociadaEm(String associadaEm) {
        this.associadaEm = associadaEm;
    }

    public String getEmissor() {
        return emissor;
    }

    public void setEmissor(String emissor) {
        this.emissor = emissor;
    }
}
