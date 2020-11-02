package br.com.zup.nossocartao.proposta;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import br.com.zup.nossocartao.proposta.validador.CpfCnpj;

@Entity
public class Proposta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@CpfCnpj
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

	@NotNull
	private StatusSolicitacao restricaoStatus;

	@Deprecated
	public Proposta() {

	}

	public Proposta(@NotBlank String cpfCnpj, @Email @NotBlank String email, @NotBlank String nome,
			@NotBlank String endereco, @NotNull @Positive BigDecimal salario) {

		this.cpfCnpj = cpfCnpj;
		this.email = email;
		this.nome = nome;
		this.endereco = endereco;
		this.salario = salario;
		this.restricaoStatus = StatusSolicitacao.NAO_ELEGIVEL;
	}

	public Long getId() {
		return id;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
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

	public void alterarStatusProposta(StatusSolicitacao alteracao) {
		this.restricaoStatus = alteracao;
	}

}
