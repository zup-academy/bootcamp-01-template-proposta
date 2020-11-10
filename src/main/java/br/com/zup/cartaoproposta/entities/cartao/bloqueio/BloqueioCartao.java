package br.com.zup.cartaoproposta.entities.cartao.bloqueio;

import br.com.zup.cartaoproposta.entities.cartao.Cartao;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Contagem de carga intrínseca da classe: 1
 */

@Entity
public class BloqueioCartao {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private LocalDateTime bloqueadoEm = LocalDateTime.now();
    private boolean ativo;
    @NotEmpty
    private String ip;
    @NotEmpty
    private String userAgent;
    @NotNull
    @Valid
    @ManyToOne
    //1
    private Cartao cartao; 

    @Deprecated
    public BloqueioCartao(){}

    public BloqueioCartao(boolean ativo, @NotEmpty String ip, @NotEmpty String userAgent, @NotNull @Valid Cartao cartao) {
        this.ativo = ativo;
        this.ip = ip;
        this.userAgent = userAgent;
        this.cartao = cartao;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getBloqueadoEm() {
        return bloqueadoEm;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public String getIp() {
        return ip;
    }

    public String getUserAgent() {
        return userAgent;
    }
}
