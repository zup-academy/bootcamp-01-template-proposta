package br.com.proposta.entidades;

import br.com.proposta.dtos.responses.BloqueioResponse;
import br.com.proposta.entidades.Enums.StatusBloqueio;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
public class Bloqueio {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotNull
    private OffsetDateTime instanteBloqueio = OffsetDateTime.now();

    @NotBlank
    private String internetProtocol;

    @NotBlank
    private String userAgent;

    @Enumerated(EnumType.STRING)
    private StatusBloqueio statusBloqueio;

    private String numeroCartao;

    @ManyToOne
    private Cartao cartao;

    public Bloqueio(@NotBlank String internetProtocol, @NotBlank String userAgent, StatusBloqueio statusBloqueio) {
        this.internetProtocol = internetProtocol;
        this.userAgent = userAgent;
        this.statusBloqueio = statusBloqueio;
    }

    public Bloqueio(List<String> IPeUserAgent, String numeroCartao) {
        this.internetProtocol = IPeUserAgent.get(0);
        this.userAgent = IPeUserAgent.get(1);
        this.numeroCartao = numeroCartao;
    }

    public String getId() {
        return id;
    }

    public void atualizaStatusAposRespostaDoLegado(String status){

        this.statusBloqueio = StatusBloqueio.valueOf(status);

    }
}
