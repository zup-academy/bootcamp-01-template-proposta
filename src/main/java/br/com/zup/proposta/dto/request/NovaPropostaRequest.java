package br.com.zup.proposta.dto.request;

import br.com.zup.proposta.annotations.CpfCnpj;
import br.com.zup.proposta.model.Proposta;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class NovaPropostaRequest {

    @NotBlank(message = "campo requerido")
    @CpfCnpj
    private String documento;

    @NotBlank(message = "campo requerido") @Email(message = "formato invalido")
    private String email;

    @NotBlank(message = "campo requerido")
    private String nome;

    @NotBlank(message = "campo requerido")
    private String endereco;

    @NotNull(message = "campo requerido")
    @Positive
    private BigDecimal salario;

    @Deprecated
    public NovaPropostaRequest() {
    }

    public NovaPropostaRequest(@NotBlank(message = "campo requerido")  String documento,
                               @NotBlank(message = "campo requerido") @Email(message = "formato invalido") String email,
                               @NotBlank(message = "campo requerido") String nome,
                               @NotBlank(message = "campo requerido") String endereco,
                               @NotBlank(message = "campo requerido") @NotNull @Positive BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public String getDocumento() {
        return documento;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public Proposta toModel() {
        return new Proposta(documento, email, nome, endereco, salario);
    }
}
