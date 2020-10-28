package br.com.proposta.models;

import javax.persistence.Embeddable;
import java.time.OffsetDateTime;
import java.util.Base64;

@Embeddable
public class Biometria {

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
