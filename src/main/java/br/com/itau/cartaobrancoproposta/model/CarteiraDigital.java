package br.com.itau.cartaobrancoproposta.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class CarteiraDigital {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotBlank
    private String idCarteiraDigital;
    @NotBlank
    @Email
    private String email;
    @NotNull
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime associadaEm = LocalDateTime.now();
    private String emissor;
    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public CarteiraDigital() {
    }

    public CarteiraDigital(@NotBlank @Email String email, String emissor) {
        this.email = email;
        this.emissor = emissor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCarteiraDigital() {
        return idCarteiraDigital;
    }

    public void setIdCarteiraDigital(String idCarteiraDigital) {
        this.idCarteiraDigital = idCarteiraDigital;
    }

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

    public void associaSolicitacao(SolicitacaoCarteira solicitacaoCarteira) {
        this.idCarteiraDigital = solicitacaoCarteira.getId();
    }
}
