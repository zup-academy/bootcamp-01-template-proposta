package br.com.proposta.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import br.com.proposta.model.Proposta;
import br.com.proposta.validator.DocumentoValido;

public class PropostaDTO {

	@Email @NotBlank
	private String email;
	@NotBlank
	private String nome;
	@NotBlank
	private String endereco;
	@NotNull @Positive
	private BigDecimal salario;
	@NotBlank @DocumentoValido
	private String documento;
	
	
	public PropostaDTO(@Email @NotBlank String email, @NotBlank String nome, @NotBlank String endereco,
			@NotNull @Positive BigDecimal salario, @NotBlank String documento) {
		this.email = email;
		this.nome = nome;
		this.endereco = endereco;
		this.salario = salario;
		this.documento = documento;
	}
	
	public Proposta toModel () {
		return new Proposta(this.email,this.nome,this.endereco,this.salario,this.documento);
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


	public String getDocumento() {
		return documento;
	}


	@Override
	public String toString() {
		return "PropostaDTO [email=" + email + ", nome=" + nome + ", endereco=" + endereco + ", salario=" + salario
				+ ", documento=" + documento + "]";
	}
	
}
