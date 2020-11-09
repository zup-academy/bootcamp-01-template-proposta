package br.com.zup.proposta.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Arrays;

@Embeddable
public class Biometria {

    @NotNull
    private byte[] digital;
    private LocalDateTime instanteCriacao;

    @Deprecated
    public Biometria(){}

    public Biometria(String digital) {
        this.digital = digital.getBytes(); //
        this.instanteCriacao = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Biometria)) return false;
        Biometria biometria = (Biometria) o;
        return Arrays.equals(digital, biometria.digital);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(digital);
    }

    @Override
    public String toString() {
        return "Biometria{" +
                "digital=" + Arrays.toString(digital) +
                '}';
    }

}
