package br.com.zup.nossocartao.proposta.controller;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import br.com.zup.nossocartao.proposta.Proposta;
import br.com.zup.nossocartao.proposta.StatusSolicitacao;

public class NovaPropostaResponse {

	private Long id;

	private String cpfCnpj;

	private String email;

	private String nome;

	private String endereco;

	private BigDecimal salario;

	@NotNull
	private StatusSolicitacao restricaoStatus;

	private String numeroCartao;

	public NovaPropostaResponse(Proposta dadosBanco) {
		this.id = dadosBanco.getId();
		this.cpfCnpj = dadosBanco.getCpfCnpj();
		this.email = dadosBanco.getEmail();
		this.nome = dadosBanco.getNome();
		this.endereco = dadosBanco.getEndereco();
		this.salario = dadosBanco.getSalario();
		this.restricaoStatus = dadosBanco.getRestricaoStatus();
		this.numeroCartao = dadosBanco.getNumeroCartao();
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

	public StatusSolicitacao getRestricaoStatus() {
		return restricaoStatus;
	}

	public String getNumeroCartao() {
		return numeroCartao;
	}

}
