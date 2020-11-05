package br.com.itau.cartaobrancoproposta.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class CarteiraDigital {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String idCarteiraDigital;
    private String email;
    private String associadaEm;
    private String emissor;
    @ManyToOne
    private Cartao cartao;

    public CarteiraDigital(String idCarteiraDigital, String email, String associadaEm, String emissor) {
        this.idCarteiraDigital = idCarteiraDigital;
        this.email = email;
        this.associadaEm = associadaEm;
        this.emissor = emissor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
