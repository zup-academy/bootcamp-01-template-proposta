package dev.arielalvesdutra.proposta.controllers.dtos;

import dev.arielalvesdutra.proposta.annotations.Documento;
import dev.arielalvesdutra.proposta.entities.Proposta;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class CadastrarPropostaDTO {

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
    @Positive(message = "{salario.positivo}")
    @NotNull(message = "{salario.obrigatorio}")
    private BigDecimal salario;

    public CadastrarPropostaDTO() {
    }

    public String getDocumento() {
        return documento;
    }

    public CadastrarPropostaDTO setDocumento(String documento) {
        this.documento = documento;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public CadastrarPropostaDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public CadastrarPropostaDTO setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public String getEndereco() {
        return endereco;
    }

    public CadastrarPropostaDTO setEndereco(String endereco) {
        this.endereco = endereco;
        return this;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public CadastrarPropostaDTO setSalario(BigDecimal salario) {
        this.salario = salario;
        return this;
    }

    public Proposta paraEntidade() {
        return new Proposta()
                .setDocumento(documento)
                .setEmail(email)
                .setSalario(salario)
                .setNome(nome)
                .setEndereco(endereco);
    }
}
