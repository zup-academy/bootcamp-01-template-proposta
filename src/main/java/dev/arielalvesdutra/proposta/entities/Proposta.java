package dev.arielalvesdutra.proposta.entities;

import dev.arielalvesdutra.proposta.annotations.Documento;
import dev.arielalvesdutra.proposta.entities.enums.PropostaStatus;
import dev.arielalvesdutra.proposta.entities.interfaces.Timestamper;
import dev.arielalvesdutra.proposta.http_clients.dtos.enums.ResultadoAnaliseStatus;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;

import static dev.arielalvesdutra.proposta.entities.enums.PropostaStatus.ELEGIVEL;
import static dev.arielalvesdutra.proposta.entities.enums.PropostaStatus.NAO_ELEGIVEL;
import static dev.arielalvesdutra.proposta.http_clients.dtos.enums.ResultadoAnaliseStatus.COM_RESTRICAO;
import static dev.arielalvesdutra.proposta.http_clients.dtos.enums.ResultadoAnaliseStatus.SEM_RESTRICAO;
import static javax.persistence.CascadeType.ALL;

@Entity
@Table(name = "proposta")
public class Proposta implements Timestamper {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Documento
    @NotBlank(message = "{documento.obrigatorio}")
    private String documento;
    @Email(message = "{email.formato_invalido}")
    @NotBlank(message = "{email.obrigatorio}")
    private String email;
    @NotBlank(message = "{nome.obrigatorio}")
    private String nome;
    @NotBlank(message = "{endereco.obrigatorio}")
    private String endereco;
    @Positive(message = "{salario.positivo")
    @NotNull(message = "{salario.obrigatorio}")
    private BigDecimal salario;

    @Enumerated(EnumType.STRING)
    private PropostaStatus status = PropostaStatus.AGUARDANDO_AVALIACAO;

    @OneToOne(cascade = ALL)
    private Cartao cartao;

    private OffsetDateTime cadastradoEm = OffsetDateTime.now();
    private OffsetDateTime atualizadoEm;

    public Proposta() {
    }

    public String getId() {
        return id;
    }

    public Proposta setId(String id) {
        this.id = id;
        return this;
    }

    public String getDocumento() {
        return documento;
    }

    public Proposta setDocumento(String documento) {
        this.documento = documento;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Proposta setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public Proposta setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public String getEndereco() {
        return endereco;
    }

    public Proposta setEndereco(String endereco) {
        this.endereco = endereco;
        return this;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public Proposta setSalario(BigDecimal salario) {
        this.salario = salario;
        return this;
    }

    public PropostaStatus getStatus() {
        return status;
    }

    public Proposta setStatus(PropostaStatus status) {
        this.status = status;
        return this;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public Proposta setCartao(Cartao cartao) {
        this.cartao = cartao;
        return this;
    }

    public Proposta setCadastradoEm(OffsetDateTime cadastradoEm) {
        this.cadastradoEm = cadastradoEm;
        return this;
    }

    public Proposta setAtualizadoEm(OffsetDateTime atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
        return this;
    }

    @Override
    public OffsetDateTime getCadastradoEm() {
        return cadastradoEm;
    }

    @Override
    public OffsetDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    /**
     * Atualiza o Status da proposta conforme o resultada da análise
     * da proposta no Serviço de Análise.
     *
     * @param resultadoAnaliseStatus
     */
    public void aplicaResultadoAnalise(ResultadoAnaliseStatus resultadoAnaliseStatus) {
        if (resultadoAnaliseStatus == COM_RESTRICAO) {
            status = NAO_ELEGIVEL;
            return;
        }

        if (resultadoAnaliseStatus == SEM_RESTRICAO) {
            status = ELEGIVEL;
            return;
        }

        throw new IllegalArgumentException("Argumento inválido para a atualização do status da proposta!");
    }

    @Override
    public String toString() {
        return "Proposta{" +
                "id='" + id + '\'' +
                ", documento='" + documento + '\'' +
                ", email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", endereco='" + endereco + '\'' +
                ", salario=" + salario +
                ", status=" + status +
                ", cadastradoEm=" + cadastradoEm +
                ", atualizadoEm=" + atualizadoEm +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Proposta proposta = (Proposta) o;
        return Objects.equals(id, proposta.id);
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
