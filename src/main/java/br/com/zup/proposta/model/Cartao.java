package br.com.zup.proposta.model;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @NotNull
    @Valid
    private Proposta proposta; //1

    @ElementCollection
    private Set<Biometria> biometrias = new HashSet<>(); //2

    @OneToMany(mappedBy = "cartao")
    private List<Bloqueio> bloqueios = new ArrayList<>();

    @Deprecated
    public Cartao() {}

    public Cartao(@NotBlank String numero, @NotNull @Valid Proposta proposta) {
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

    public String emailDonocartao(){
        return proposta.getEmail();
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
