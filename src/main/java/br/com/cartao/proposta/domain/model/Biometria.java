package br.com.cartao.proposta.domain.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "biometria")
public class Biometria {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @ElementCollection
    @NotNull
    private final List<String> fingerprint;
    @NotBlank
    private final String idCartao;


    public Biometria(@NotNull @Size(min = 1) List<String> fingerprint, String idCartao) {

        this.fingerprint = fingerprint;
        this.idCartao = idCartao;
    }

    public String getId() {
        return id;
    }

    public List<String> getFingerprint() {
        return fingerprint;
    }

    public String getIdCartao() {
        return idCartao;
    }

    @Override
    public String toString() {
        return "Biometria{" +
                "fingerprint=" + fingerprint +
                ", idCartao='" + idCartao + '\'' +
                '}';
    }
}
