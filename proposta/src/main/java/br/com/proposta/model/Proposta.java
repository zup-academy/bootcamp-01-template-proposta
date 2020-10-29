package br.com.proposta.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.util.Assert;

import br.com.proposta.dto.enums.StatusAvaliacaoProposta;
import br.com.proposta.validator.DocumentoValido;

@Entity
public class Proposta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
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
	@NotNull
	private StatusAvaliacaoProposta statusAvaliacao;
	@OneToOne(mappedBy = "proposta",cascade = CascadeType.MERGE)	
	private Cartao cartao;

	@Deprecated
	public Proposta() {
	}
	
	public Proposta(@Email @NotBlank String email, @NotBlank String nome, @NotBlank String endereco,
			@NotNull @Positive BigDecimal salario, @NotBlank String documento) {
		this.email = email;
		this.nome = nome;
		this.endereco = endereco;
		this.salario = salario;
		this.documento = documento;
		this.statusAvaliacao = StatusAvaliacaoProposta.nao_elegivel;
	}

	public Long getId() {
		return id;
	}
	
	public String getDocumento() {
		return documento;
	}
	
	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getEndereco() {
		return endereco;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public StatusAvaliacaoProposta getStatusAvaliacao() {
		return statusAvaliacao;
	}

	public Cartao getCartao() {
		return cartao;
	}

	public void setStatusAvaliacao(StatusAvaliacaoProposta statusAvaliacao) {
		this.statusAvaliacao = statusAvaliacao;
	}
	
	public void associaCartao(Cartao cartao) {
		Assert.isTrue(this.statusAvaliacao.equals(StatusAvaliacaoProposta.elegivel),"nao rola associar cartao com proposta nao elegivel");
		this.cartao = cartao;
	}

	@Override
	public String toString() {
		return "Proposta [id=" + id + ", email=" + email + ", nome=" + nome + ", endereco=" + endereco + ", salario="
				+ salario + ", documento=" + documento + ", statusAvaliacao=" + statusAvaliacao + ", cartao=" + cartao
				+ "]";
	}
	
}
