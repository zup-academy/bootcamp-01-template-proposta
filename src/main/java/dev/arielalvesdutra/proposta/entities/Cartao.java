package dev.arielalvesdutra.proposta.entities;

import dev.arielalvesdutra.proposta.entities.interfaces.Timestamper;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "cartao")
public class Cartao implements Timestamper {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotNull(message = "{emitidoEm.obrigatorio}")
    private OffsetDateTime emitidoEm;
    @NotBlank(message = "{titular.obrigatorio}")
    private String titular;
    @NotNull(message = "{limite.obrigatorio}")
    private BigDecimal limite;
    /**
     * ID do cartão gerado no legado (Serviço de Cartões).
     *
     * Não pode ser utilizado para APIs públicas.
     */
    @NotBlank(message = "ID do legado é obrigatório!")
    private String legadoId;

    private OffsetDateTime cadastradoEm = OffsetDateTime.now();
    private OffsetDateTime atualizadoEm;

    @OneToOne(mappedBy = "cartao")
    private Proposta proposta;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "cartao", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Biometria> biometrias;

    public String getId() {
        return id;
    }

    public Cartao setId(String id) {
        this.id = id;
        return this;
    }

    public OffsetDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public Cartao setEmitidoEm(OffsetDateTime emitidoEm) {
        this.emitidoEm = emitidoEm;
        return this;
    }

    public String getTitular() {
        return titular;
    }

    public Cartao setTitular(String titular) {
        this.titular = titular;
        return this;
    }

    public BigDecimal getLimite() {
        return limite;
    }

    public Cartao setLimite(BigDecimal limite) {
        this.limite = limite;
        return this;
    }

    public Proposta getProposta() {
        return proposta;
    }

    public Cartao setProposta(Proposta proposta) {
        this.proposta = proposta;
        return this;
    }

    public Set<Biometria> getBiometrias() {
        return biometrias;
    }

    public Cartao setBiometrias(Set<Biometria> biometrias) {
        this.biometrias = biometrias;
        return this;
    }

    public Cartao addBiometria(Biometria biometria) {
        biometria.setCartao(this);
        biometrias.add(biometria);
        return this;
    }

    public String getLegadoId() {
        return legadoId;
    }

    public Cartao setLegadoId(String legadoId) {
        this.legadoId = legadoId;
        return this;
    }

    @Override
    public OffsetDateTime getCadastradoEm() {
        return cadastradoEm;
    }

    public Cartao setCadastradoEm(OffsetDateTime cadastradoEm) {
        this.cadastradoEm = cadastradoEm;
        return this;
    }

    @Override
    public OffsetDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    public Cartao setAtualizadoEm(OffsetDateTime atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cartao cartao = (Cartao) o;
        return Objects.equals(id, cartao.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @PreUpdate
    private void preUpdate() {
        atualizadoEm = OffsetDateTime.now();
    }
}
