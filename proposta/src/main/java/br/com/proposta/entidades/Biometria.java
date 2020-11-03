package br.com.proposta.entidades;


import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.Base64;

@Entity
public class Biometria {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private byte[] biometria;

    @NotNull
    private OffsetDateTime criadaEm = OffsetDateTime.now();

    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public Biometria(){}

    public Biometria(String biometria) {

        this.biometria = Base64.getDecoder().decode(biometria);
    }

    public String getId() {
        return id;
    }

    public void associaCartao(Cartao cartao){
        this.cartao = cartao;
    }

}
