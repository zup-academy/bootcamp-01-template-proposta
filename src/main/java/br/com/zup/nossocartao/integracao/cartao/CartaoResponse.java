package br.com.zup.nossocartao.integracao.cartao;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class CartaoResponse {

	private String id;

	private String emitidoEm;

	private String titular;

	private List<Bloqueio> bloqueios;

	private List<Aviso> avisos;

	private List<Carteira> carteiras;

	private List<Parcela> parcela;

	private String limite;

	private List<Renegociacao> renegociacao;

	private Vencimento vencimento;

	private String idProposta;

	@Deprecated
	public CartaoResponse() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmitidoEm() {
		return emitidoEm;
	}

	public void setEmitidoEm(String emitidoEm) {
		this.emitidoEm = emitidoEm;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public List<Bloqueio> getBloqueios() {
		return bloqueios;
	}

	public void setBloqueios(List<Bloqueio> bloqueios) {
		this.bloqueios = bloqueios;
	}

	public List<Aviso> getAvisos() {
		return avisos;
	}

	public void setAvisos(List<Aviso> avisos) {
		this.avisos = avisos;
	}

	public List<Carteira> getCarteiras() {
		return carteiras;
	}

	public void setCarteiras(List<Carteira> carteiras) {
		this.carteiras = carteiras;
	}

	public List<Parcela> getParcela() {
		return parcela;
	}

	public void setParcela(List<Parcela> parcela) {
		this.parcela = parcela;
	}

	public String getLimite() {
		return limite;
	}

	public void setLimite(String limite) {
		this.limite = limite;
	}

	public List<Renegociacao> getRenegociacao() {
		return renegociacao;
	}

	public void setRenegociacao(List<Renegociacao> renegociacao) {
		this.renegociacao = renegociacao;
	}

	public Vencimento getVencimento() {
		return vencimento;
	}

	public void setVencimento(Vencimento vencimento) {
		this.vencimento = vencimento;
	}

	public String getIdProposta() {
		return idProposta;
	}

	public void setIdProposta(String idProposta) {
		this.idProposta = idProposta;
	}

}
