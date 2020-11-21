package br.com.zup.nossocartao.integracao.carteiradigital;

public class SamsungPayResponseFeign {

	private String resultado;

	private String id;

	@Deprecated
	public SamsungPayResponseFeign() {
	}

	public SamsungPayResponseFeign(String resultado, String id) {
		this.resultado = resultado;
		this.id = id;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
