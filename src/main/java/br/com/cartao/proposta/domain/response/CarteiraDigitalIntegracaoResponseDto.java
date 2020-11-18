package br.com.cartao.proposta.domain.response;

import java.time.LocalDateTime;

public class CarteiraDigitalIntegracaoResponseDto {

    private String id;
    private String email;
    private LocalDateTime associadaEm;
    private String emissor;

    @Deprecated
    public CarteiraDigitalIntegracaoResponseDto() {
    }

    public CarteiraDigitalIntegracaoResponseDto(String id, String email, LocalDateTime associadaEm, String emissor) {
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

    @Override
    public String toString() {
        return "CarteiraDigitalResponseDto{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", associadaEm=" + associadaEm +
                ", emissor='" + emissor + '\'' +
                '}';
    }
}
