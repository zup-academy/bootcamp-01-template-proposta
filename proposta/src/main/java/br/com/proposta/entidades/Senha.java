package br.com.proposta.entidades;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
public class Senha {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotNull
    private OffsetDateTime instanteSolicitacao;

    @NotBlank
    private String internetProtocol;

    @NotBlank
    private String userAgent;

    private String numeroCartao;

    @Deprecated
    public Senha(){}

    public Senha(List<String> ipEuserAgent, String numeroCartao) {
        this.instanteSolicitacao = OffsetDateTime.now();
        this.internetProtocol = ipEuserAgent.get(0);
        this.userAgent = ipEuserAgent.get(1);
        this.numeroCartao = numeroCartao;
    }



}
