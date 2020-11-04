package br.com.zup.proposta.model;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.*;

@Entity
public class Cartao {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @NotBlank
    private String numero;
    @OneToOne
    private Proposta proposta; //1

    @ElementCollection
    private Set<Biometria> biometrias = new HashSet<>(); //2

    @Deprecated
    public Cartao() {}

    public Cartao(@NotBlank String numero, Proposta proposta) {
        this.numero = numero;
        this.proposta = proposta;
    }

    public UUID getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public Set<Biometria> getBiometrias() {
        return biometrias;
    }

    public void associaBiometria(String digital){
        this.biometrias.add(new Biometria(digital));
    }

    @Override
    public String toString() {
        return "Cartao{" +
                "numero='" + numero + '\'' +
                '}';
    }

}
