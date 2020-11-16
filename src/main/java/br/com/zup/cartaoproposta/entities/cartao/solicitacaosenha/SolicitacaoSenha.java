package br.com.zup.cartaoproposta.entities.cartao.solicitacaosenha;

import br.com.zup.cartaoproposta.entities.cartao.Cartao;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Contagem de carga intrínseca da classe: 1
 */

@Entity
public class SolicitacaoSenha {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotBlank
    private String ip;

    @NotBlank
    private String userAgent;

    @NotNull
    @Valid
    @ManyToOne
    //1
    private Cartao cartao;

    private LocalDateTime solicitadoEm = LocalDateTime.now();

    @Deprecated
    public SolicitacaoSenha(){}

    public SolicitacaoSenha(@NotBlank String ip, @NotBlank String userAgent, @NotNull @Valid Cartao cartao) {
        this.ip = ip;
        this.userAgent = userAgent;
        this.cartao = cartao;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "SolicitacaoSenha{" +
                "id='" + id + '\'' +
                ", ip='" + ip + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", cartao=" + cartao +
                ", solicitadoEm=" + solicitadoEm +
                '}';
    }
}
