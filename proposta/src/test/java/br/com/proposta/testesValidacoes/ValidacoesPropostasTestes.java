package br.com.proposta.testesValidacoes;

import br.com.proposta.entidades.Enums.StatusAvaliacaoProposta;
import br.com.proposta.validacoes.interfaces.CpfCnpj;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class ValidacoesPropostasTestes {

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
    @CpfCnpj
    private String identificacao;

    private StatusAvaliacaoProposta status;

}
