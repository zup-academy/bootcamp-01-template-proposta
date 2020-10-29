package br.com.proposta.models;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.OffsetDateTime;
import java.util.Base64;

@Entity
public class Biometria {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private byte[] biometria;

    private OffsetDateTime criadaEm;

    @Deprecated
    public Biometria(){}

    public Biometria(String biometria) {

        this.biometria = Base64.getEncoder().encode(biometria.getBytes());

        this.criadaEm = OffsetDateTime.now();

    }

    public byte[] getBiometria() {
        return biometria;
    }

    public void setBiometria(byte[] biometria) {
        this.biometria = biometria;
    }
}
