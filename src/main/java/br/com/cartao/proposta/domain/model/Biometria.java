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

    @NotNull
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<FingerPrint> fingerprint;
    @NotBlank
    private String idCartao;

    @Deprecated
    public Biometria() {
    }

    public Biometria(@NotNull @Size(min = 1) List<FingerPrint> fingerprint, String idCartao) {

        this.fingerprint = fingerprint;
        this.idCartao = idCartao;
    }

    public String getId() {
        return id;
    }

    public List<FingerPrint> getFingerprint() {
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
