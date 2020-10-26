package br.com.proposta.models;

import br.com.proposta.validacoes.interfaces.CpfCnpj;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String endereco;

    @NotNull
    @Positive
    private BigDecimal salario;

    @NotBlank
    @CpfCnpj(message = "O CPF ou CNPJ devem ser válidos")
    private String numeroIdentificacao;

    @Deprecated
    public Proposta(){}

    public Proposta(@NotBlank String nome, @NotBlank @Email String email, @NotBlank String endereco,
                    @NotNull @Positive BigDecimal salario, @NotBlank @CpfCnpj String numeroIdentificacao) {
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
        this.salario = salario;
        this.numeroIdentificacao = numeroIdentificacao;
    }

    public Long getId() {
        return id;
    }

}
