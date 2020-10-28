package br.com.proposta.models;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cartao")
public class Cartao {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private OffsetDateTime emitidoEm;

    private String titular;

    @ElementCollection
    private Set<Biometria> biometria = new HashSet();

    @OneToOne
    private Proposta proposta;

    @Deprecated
    public Cartao(){}

    public Cartao(String titular, String idProposta) {
        this.emitidoEm = OffsetDateTime.now();
        this.titular = titular;
    }

    public String getId() {
        return id;
    }

    public Cartao(String id) {
        this.id = id;
    }

    public void adicionaBiometria(Biometria biometria){
        this.biometria.add(biometria);
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public Proposta getProposta() {
        return proposta;
    }

    public void setProposta(Proposta proposta) {
        this.proposta = proposta;
    }
}
