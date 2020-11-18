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

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

@Entity
public class Proposta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
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

	private String numeroCartao;

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

	public void criptografar(String chaveCriptogfia) {
		TextEncryptor textEncryptor = Encryptors.text(this.getClass().getName(), chaveCriptogfia);
		this.cpfCnpj = textEncryptor.encrypt(this.cpfCnpj);
	}

	public void decriptografar(String chaveCriptogfia) {
		TextEncryptor textEncryptor = Encryptors.text(this.getClass().getName(), chaveCriptogfia);
		this.cpfCnpj = textEncryptor.decrypt(this.cpfCnpj);
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

	public StatusSolicitacao getRestricaoStatus() {
		return restricaoStatus;
	}

	public void setRestricaoStatus(StatusSolicitacao restricaoStatus) {
		this.restricaoStatus = restricaoStatus;
	}

	public String getNumeroCartao() {
		return numeroCartao;
	}

	public void setNumeroCartao(String numeroCartao) {
		this.numeroCartao = numeroCartao;
	}

	public void setId(Long id) {
		this.id = id;
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

}
