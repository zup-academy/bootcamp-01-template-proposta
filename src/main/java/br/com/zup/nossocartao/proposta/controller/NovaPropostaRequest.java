package br.com.zup.nossocartao.proposta.controller;

import java.math.BigDecimal;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import br.com.zup.nossocartao.proposta.Proposta;

public class NovaPropostaRequest {

	@NotBlank
	// @CpfCnjp
	private String cpfCnpj;

	@Email
	@NotBlank
	private String email;

	@NotBlank
	private String nome;

	@NotBlank
	private String endereco;

	@NotNull
	@Positive
	private BigDecimal salario;

	public NovaPropostaRequest(@NotBlank String cpfCnpj, @Email @NotBlank String email, @NotBlank String nome,
			@NotBlank String endereco, @NotNull @Positive BigDecimal salario) {
		this.cpfCnpj = cpfCnpj;
		this.email = email;
		this.nome = nome;
		this.endereco = endereco;
		this.salario = salario;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}

	public Proposta gerarProposta() {

		Proposta novaProposta = new Proposta(cpfCnpj, email, nome, endereco, salario);
		return novaProposta;
	}

}
