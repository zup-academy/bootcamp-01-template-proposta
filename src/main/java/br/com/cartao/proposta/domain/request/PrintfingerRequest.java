package br.com.cartao.proposta.domain.request;

import br.com.cartao.proposta.domain.model.FingerPrint;

import javax.validation.constraints.NotBlank;

/**
 * Carga intrínseca máxima permitida - 9
 * Carga intrínseca da classe - 1
 */

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
    // +1
    public FingerPrint toModel(){
        return new FingerPrint(this.dedo, this.digital);
    }
}
