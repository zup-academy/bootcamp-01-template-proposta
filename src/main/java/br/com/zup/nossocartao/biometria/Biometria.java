package br.com.zup.nossocartao.biometria;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Biometria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idBiometria;

	@Lob
	@NotNull
	private byte[] fingerprint;

	@NotBlank
	private String idCartao;

	@Deprecated
	public Biometria() {
	}

	public Biometria(@NotNull byte[] fingerprint, @NotBlank String idCartao) {
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
