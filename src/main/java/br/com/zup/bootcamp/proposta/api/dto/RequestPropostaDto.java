package br.com.zup.bootcamp.proposta.api.dto;

import br.com.zup.bootcamp.proposta.api.handler.Unique;
import br.com.zup.bootcamp.proposta.domain.entity.Proposta;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class RequestPropostaDto {

    @NotBlank @Unique(domainClass = Proposta.class, fieldName = "documento")
    private final String documento;
    @Email
    private final String email;
    @NotBlank
    private final String nome;
    @NotBlank(message = "obrigatorio")
    private final String endereco;
    @Positive
    private final BigDecimal salario;

    public RequestPropostaDto(@NotBlank String documento, @Email String email, @NotBlank String nome, @NotBlank String endereco, @Positive BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Proposta toEntity() {
        return new Proposta(documento, email, nome, endereco, salario);
    }

    public boolean documentoValido() {
        Assert.hasLength(documento, "documento null, não foi possível fazer a validação");

        CPFValidator cpfValidator = new CPFValidator();
        cpfValidator.initialize(null);

        CNPJValidator cnpjValidator = new CNPJValidator();
        cnpjValidator.initialize(null);

        return cpfValidator.isValid(documento, null) || cnpjValidator.isValid(documento, null);
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
}
