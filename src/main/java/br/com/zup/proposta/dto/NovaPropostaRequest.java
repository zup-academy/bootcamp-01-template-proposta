package br.com.zup.proposta.dto;

import br.com.zup.proposta.model.Proposta;
import br.com.zup.proposta.validations.CpfCnpj;
import org.springframework.security.crypto.encrypt.Encryptors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class NovaPropostaRequest {

    @NotBlank
    @CpfCnpj(message = "Documento inválido")
    private String documento;
    private String email;
    @NotBlank
    private String nome;
    @NotBlank
    private String endereco;
    @NotNull @Positive
    private BigDecimal salarioBruto;

    public NovaPropostaRequest(@NotBlank String documento,
                               @NotBlank String nome,
                               @NotBlank String endereco,
                               @NotNull @Positive BigDecimal salarioBruto) {
        this.documento = documento;
        this.nome = nome;
        this.endereco = endereco;
        this.salarioBruto = salarioBruto;
    }

    public String getDocumento() {
        return documento;
    }

    public Proposta toProposta(@NotBlank @Email String emailAutenticado) {

        this.email = emailAutenticado;

        return new Proposta(this.documento, this.email, this.nome,
                this.endereco, this.salarioBruto);
    }

}
