package br.com.cartao.proposta.domain.response;

import java.time.LocalDate;

public class AvisoViagemIntegracaoResponseDto {

    private LocalDate validoAte;
    private String destino;

    @Deprecated
    public AvisoViagemIntegracaoResponseDto() {
    }

    public AvisoViagemIntegracaoResponseDto(LocalDate validoAte, String destino) {
        this.validoAte = validoAte;
        this.destino = destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }

    public String getDestino() {
        return destino;
    }

    @Override
    public String toString() {
        return "AvisoViagemResponseDto{" +
                "validoAte=" + validoAte +
                ", destino='" + destino + '\'' +
                '}';
    }
}
