package br.com.itau.cartaobrancoproposta.model;

import br.com.itau.cartaobrancoproposta.error.ApiErrorException;
import br.com.itau.cartaobrancoproposta.validator.CpfOuCnpj;
import br.com.itau.cartaobrancoproposta.validator.VerificaPropostaMesmoSolicitante;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Base64;

public class PropostaRequest {

    @NotBlank
    @CpfOuCnpj
    private final String documento;
    @NotBlank
    @Email
    private final String email;
    @NotBlank
    private final String nome;
    @NotBlank
    private final String endereco;
    @NotNull
    @Positive
    private final BigDecimal salario;

    public String getDocumento() {
        return documento;
    }

    public PropostaRequest(@NotBlank String documento, @NotBlank @Email String email, @NotBlank String nome,
                           @NotBlank String endereco, @NotNull @Positive BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
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
        String documentoCriptografado = Base64.getEncoder().encodeToString(this.documento.getBytes());

        return new Proposta(documentoCriptografado, this.email, this.nome, this.endereco, this.salario, Restricao.PENDENTE);
    }

    public void verificaDocumentoValido(VerificaPropostaMesmoSolicitante verificadorProposta) { //1
        if (!verificadorProposta.estaValido(this.documento)) { //1
            throw new ApiErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "Documento (" + this.documento + ") já está cadastrado"); //1
        }
    }
}
