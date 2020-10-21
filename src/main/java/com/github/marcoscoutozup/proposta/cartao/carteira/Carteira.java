package com.github.marcoscoutozup.proposta.cartao.carteira;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class Carteira {

    private String email;
    private LocalDateTime associadaEm;
    private String emissor;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getAssociadaEm() {
        return associadaEm;
    }

    public void setAssociadaEm(LocalDateTime associadaEm) {
        this.associadaEm = associadaEm;
    }

    public String getEmissor() {
        return emissor;
    }

    public void setEmissor(String emissor) {
        this.emissor = emissor;
    }

    @Override
    public String toString() {
        return "Carteira{" +
                "email='" + email + '\'' +
                ", associadaEm=" + associadaEm +
                ", emissor='" + emissor + '\'' +
                '}';
    }
}
