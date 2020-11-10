package br.com.proposta.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

import br.com.proposta.dto.enums.StatusAvaliacaoProposta;


//Contagem de Pontos - TOTAL:2
//1 - Cartao
//1 - StatusAvaliacaoProposta

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
	@NotBlank
	private String documento;
	@NotNull @Enumerated(EnumType.STRING)
	private StatusAvaliacaoProposta statusAvaliacao;
	@OneToOne(mappedBy = "proposta",cascade = CascadeType.MERGE)	
	private Cartao cartao;
	@NotNull
	private String idKeyclock;

	@Deprecated
	public Proposta() {
	}
	
	public Proposta(@Email @NotBlank String email, @NotBlank String nome, @NotBlank String endereco,
			@NotNull @Positive BigDecimal salario, @NotBlank String documento) {
		this.email = email;
		this.nome = nome;
		this.endereco = endereco;
		this.salario = salario;
		this.documento = hashDocumento(documento);
		this.statusAvaliacao = StatusAvaliacaoProposta.nao_elegivel;
	}

	
	public boolean verificaUsuario(String idLoguin) {
		return this.idKeyclock.equals(idLoguin);
	}
	
	public static String hashDocumento (String documento) {
		return new BCryptPasswordEncoder().encode(documento);
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

	public String getIdKeyclock() {
		return idKeyclock;
	}

	public void setIdKeyclock(String idKeyclock) {
		this.idKeyclock = idKeyclock;
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
				+ ", idKeyclock=" + idKeyclock + "]";
	}
	
}
