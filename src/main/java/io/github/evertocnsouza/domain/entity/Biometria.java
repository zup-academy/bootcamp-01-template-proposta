package io.github.evertocnsouza.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@Entity
public class Biometria {

    @Id
    @GeneratedValue(generator = "uuid4")
    private UUID id;

    @NotNull
    private byte[] digital;

    @NotNull
    private LocalDateTime instanteCadastro = LocalDateTime.now();

    public Biometria() {
    }

    public Biometria(String digital) {
        this.digital = digital.getBytes();
    }

    public UUID getId() {
        return id;
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

}
