package br.com.zup.nossocartao.biometria;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Biometria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idBiometria;

	@Lob
	private byte[] fingerprint;

	private String idCartao;

	@Deprecated
	public Biometria() {
	}

	public Biometria(byte[] fingerprint, String idCartao) {
		this.fingerprint = fingerprint;
		this.idCartao = idCartao;
	}

	public Long getIdBiometria() {
		return idBiometria;
	}

	public void setIdBiometria(Long idBiometria) {
		this.idBiometria = idBiometria;
	}

	public byte[] getFingerprint() {
		return fingerprint;
	}

	public void setFingerprint(byte[] fingerprint) {
		this.fingerprint = fingerprint;
	}

	public String getIdCartao() {
		return idCartao;
	}

	public void setIdCartao(String idCartao) {
		this.idCartao = idCartao;
	}

}
