package br.com.itau.cartaobrancoproposta.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class AvisoViagem {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String validoAte;
    private String destino;
    @ManyToOne
    private Cartao cartao;

    public AvisoViagem(String validoAte, String destino) {
        this.validoAte = validoAte;
        this.destino = destino;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValidoAte() {
        return validoAte;
    }

    public void setValidoAte(String validoAte) {
        this.validoAte = validoAte;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }
}
