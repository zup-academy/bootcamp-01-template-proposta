package br.com.zup.cartaoproposta.entities.cartao;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

/**
 * Contagem de carga intrínseca da classe: 0
 */

@Embeddable
public class CarteiraCartaoRetorno {

    private String id;
    private String email;
    private LocalDateTime associadaEm;
    private String emissor;

    @Deprecated
    public CarteiraCartaoRetorno(){}

    public CarteiraCartaoRetorno(String id, String email, LocalDateTime associadaEm, String emissor) {
        this.id = id;
        this.email = email;
        this.associadaEm = associadaEm;
        this.emissor = emissor;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getAssociadaEm() {
        return associadaEm;
    }

    public String getEmissor() {
        return emissor;
    }
}
