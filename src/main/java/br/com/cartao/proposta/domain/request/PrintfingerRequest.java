package br.com.cartao.proposta.domain.request;

import br.com.cartao.proposta.domain.model.FingerPrint;

import javax.validation.constraints.NotBlank;

public class PrintfingerRequest {

    @NotBlank
    private final String dedo;
    @NotBlank
    private final String digital;

    public PrintfingerRequest(@NotBlank String dedo,@NotBlank String digital) {
        this.dedo = dedo;
        this.digital = digital;
    }

    public String getDedo() {
        return dedo;
    }

    public String getDigital() {
        return digital;
    }

    public FingerPrint toModel(){
        return new FingerPrint(this.dedo, this.digital);
    }
}
