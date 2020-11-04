package br.com.zup.proposta.integracao.cartao.biometria;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Biometria {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private @NotBlank String digital;
    private @NotNull LocalDateTime instante = LocalDateTime.now();

    @Deprecated
    public Biometria() {
    }

    public Biometria(@NotBlank String digital) {
        this.digital = digital;
    }
}
