package br.com.proposta.dto;

import javax.validation.constraints.NotBlank;

import br.com.proposta.model.Biometria;

public class BiometriaDTO {

	@NotBlank
	private String digital;

	@Deprecated
	public BiometriaDTO() {
	}
	
	public BiometriaDTO(@NotBlank String digital) {
		this.digital = digital;
	}
	
	public Biometria toModel() {
		return new Biometria(this.digital);
	}

	public String getDigital() {
		return digital;
	}

	public void setDigital(String digital) {
		this.digital = digital;
	}

	@Override
	public String toString() {
		return "BiometriaDTO [digital=" + digital + "]";
	}
	
	
}
