package br.com.zup.nossocartao.integracao.cartao;

public class Carteira {

	private String id;

	private String email;

	private String associadaEm;

	private String emissor;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAssociadaEm() {
		return associadaEm;
	}

	public void setAssociadaEm(String associadaEm) {
		this.associadaEm = associadaEm;
	}

	public String getEmissor() {
		return emissor;
	}

	public void setEmissor(String emissor) {
		this.emissor = emissor;
	}
}
