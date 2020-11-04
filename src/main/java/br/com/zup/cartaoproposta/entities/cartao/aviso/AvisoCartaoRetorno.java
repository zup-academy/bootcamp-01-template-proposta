package br.com.zup.cartaoproposta.entities.cartao.aviso;

import java.time.LocalDate;

/**
 * Contagem de carga intrínseca da classe: 0
 */

public class AvisoCartaoRetorno {

    private LocalDate validoAte;
    private String destino;

    @Deprecated
    public AvisoCartaoRetorno(){}

    public AvisoCartaoRetorno(LocalDate validoAte, String destino) {
        this.validoAte = validoAte;
        this.destino = destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }

    public String getDestino() {
        return destino;
    }
}
