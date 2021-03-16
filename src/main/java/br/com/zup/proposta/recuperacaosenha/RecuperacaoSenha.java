package br.com.zup.proposta.recuperacaosenha;

import br.com.zup.proposta.cartao.Cartao;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class RecuperacaoSenha {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String ip;
    private String userAgent;
    private @ManyToOne Cartao cartao;
    private LocalDateTime instante = LocalDateTime.now();

    @Deprecated
    public RecuperacaoSenha() {
    }

    public RecuperacaoSenha(String ip, String userAgent, Cartao cartao) {
        this.ip = ip;
        this.userAgent = userAgent;
        this.cartao = cartao;
    }

    public String getId() {
        return id;
    }
}
