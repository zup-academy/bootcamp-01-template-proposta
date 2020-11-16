package dev.arielalvesdutra.proposta.controllers.dtos;

import dev.arielalvesdutra.proposta.entities.Proposta;
import dev.arielalvesdutra.proposta.entities.enums.PropostaStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class PropostaResponseDTO {

    private String id;
    private String nome;
    private String email;
    private String documento;
    private String endereco;
    private BigDecimal salario;
    private PropostaStatus status;
    private OffsetDateTime cadastradoEm;
    private OffsetDateTime atualizadoEm;

    public PropostaResponseDTO() {}

    public PropostaResponseDTO(Proposta proposta) {
        setId(proposta.getId());
        setNome(proposta.getNome());
        setEmail(proposta.getEmail());
        setDocumento(proposta.getDocumento());
        setEndereco(proposta.getEndereco());
        setSalario(proposta.getSalario());
        setStatus(proposta.getStatus());
        setAtualizadoEm(proposta.getAtualizadoEm());
        setCadastradoEm(proposta.getCadastradoEm());
    }

    public String getId() {
        return id;
    }

    public PropostaResponseDTO setId(String id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public PropostaResponseDTO setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public PropostaResponseDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getDocumento() {
        return documento;
    }

    public PropostaResponseDTO setDocumento(String documento) {
        this.documento = documento;
        return this;
    }

    public String getEndereco() {
        return endereco;
    }

    public PropostaResponseDTO setEndereco(String endereco) {
        this.endereco = endereco;
        return this;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public PropostaResponseDTO setSalario(BigDecimal salario) {
        this.salario = salario;
        return this;
    }

    public PropostaStatus getStatus() {
        return status;
    }

    public PropostaResponseDTO setStatus(PropostaStatus status) {
        this.status = status;
        return this;
    }

    public OffsetDateTime getCadastradoEm() {
        return cadastradoEm;
    }

    public PropostaResponseDTO setCadastradoEm(OffsetDateTime cadastradoEm) {
        this.cadastradoEm = cadastradoEm;
        return this;
    }

    public OffsetDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    public PropostaResponseDTO setAtualizadoEm(OffsetDateTime atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
        return this;
    }

    @Override
    public String toString() {
        return "PropostaResponseDTO{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", documento='" + documento + '\'' +
                ", endereco='" + endereco + '\'' +
                ", salario=" + salario +
                ", status=" + status +
                ", cadastradoEm=" + cadastradoEm +
                ", atualizadoEm=" + atualizadoEm +
                '}';
    }
}
