package io.github.evertocnsouza.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

@Entity
public class Biometria {

    @Id
    @GeneratedValue(generator = "uuid4")
    private UUID id;

    @NotNull
    private byte[] digital;

    private LocalDateTime instante = LocalDateTime.now();

    public Biometria() {
    }

    public Biometria(String digital) {
        this.digital = Base64.getEncoder().encode(digital.getBytes());
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

    public UUID getId() {
        return id;
    }
}
