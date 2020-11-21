package br.com.zup.nossocartao.integracao.cartao;

public class Bloqueio {

	private String id;

	private String bloqueadoEm;

	private String sistemaResponsavel;

	private boolean ativo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBloqueadoEm() {
		return bloqueadoEm;
	}

	public void setBloqueadoEm(String bloqueadoEm) {
		this.bloqueadoEm = bloqueadoEm;
	}

	public String getSistemaResponsavel() {
		return sistemaResponsavel;
	}

	public void setSistemaResponsavel(String sistemaResponsavel) {
		this.sistemaResponsavel = sistemaResponsavel;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
}
