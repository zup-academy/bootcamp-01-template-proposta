package br.com.proposta.models;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private OffsetDateTime emitidoEm;

    private String titular;

    private String idProposta;

    @OneToOne
    private Proposta proposta;

    @Deprecated
    public Cartao(){}

    public Cartao(String titular, String idProposta) {
        this.emitidoEm = OffsetDateTime.now();
        this.titular = titular;
        this.idProposta = idProposta;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getIdProposta() {
        return idProposta;
    }

    public void setIdProposta(String idProposta) {
        this.idProposta = idProposta;
    }

    public Proposta getProposta() {
        return proposta;
    }

    public void setProposta(Proposta proposta) {
        this.proposta = proposta;
    }
}
