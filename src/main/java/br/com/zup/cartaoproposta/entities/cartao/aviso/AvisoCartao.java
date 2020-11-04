package br.com.zup.cartaoproposta.entities.cartao.aviso;

import javax.persistence.Embeddable;
import java.time.LocalDate;

/**
 * Contagem de carga intrínseca da classe: 0
 */

@Embeddable
public class AvisoCartao {

    private LocalDate validoAte;
    private String destino;

    public AvisoCartao(LocalDate validoAte, String destino) {
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
