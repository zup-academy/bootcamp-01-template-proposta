package br.com.cartao.proposta.domain.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class FingerPrint {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private String dedo;
    private String digital;

    @Deprecated
    public FingerPrint() {
    }

    public FingerPrint(String dedo, String digital) {
        this.dedo = dedo;
        this.digital = digital;
    }

    public String getId() {
        return id;
    }

    public String getDedo() {
        return dedo;
    }

    public String getDigital() {
        return digital;
    }

    @Override
    public String toString() {
        return "Printfinger{" +
                "dedo='" + dedo + '\'' +
                ", fingerPrint='" + digital + '\'' +
                '}';
    }
}
