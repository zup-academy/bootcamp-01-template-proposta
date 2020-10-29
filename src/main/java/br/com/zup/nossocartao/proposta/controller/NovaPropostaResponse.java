package br.com.zup.nossocartao.proposta.controller;

import java.math.BigDecimal;

import br.com.zup.nossocartao.proposta.Proposta;

public class NovaPropostaResponse {

	private Long id;

	private String cpfCnpj;

	private String email;

	private String nome;

	private String endereco;

	private BigDecimal salario;

	public NovaPropostaResponse(Proposta dadosBanco) {
		this.id = dadosBanco.getId();
		this.cpfCnpj = dadosBanco.getCpfCnpj();
		this.email = dadosBanco.getEmail();
		this.nome = dadosBanco.getNome();
		this.endereco = dadosBanco.getEndereco();
		this.salario = dadosBanco.getSalario();
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

}
