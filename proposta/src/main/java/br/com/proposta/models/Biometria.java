package br.com.proposta.models;

import javax.persistence.Embeddable;
import java.time.OffsetDateTime;

@Embeddable
public class Biometria {

    private byte[] biometria;

    private OffsetDateTime criadaEm;

    @Deprecated
    public Biometria(){}

    public Biometria(byte[] biometria) {
        this.biometria = biometria;
        this.criadaEm = OffsetDateTime.now();
    }

    public byte[] getBiometria() {
        return biometria;
    }

    public void setBiometria(byte[] biometria) {
        this.biometria = biometria;
    }
}
