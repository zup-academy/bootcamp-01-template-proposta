package br.com.cartao.proposta.domain.model;

import br.com.cartao.proposta.domain.request.InformacaoRede;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Carga intrínseca máxima permitida - 9
 * Carga intrínseca da classe - 2
 */

@Entity
@Table(name = "recuperaSenha")
public class RecuperaSenha {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private final LocalDateTime instante;
    private final String ipAddressCliente;
    private final String userAgent;
    @ManyToOne
    // +1
    private final Cartao cartao;
    // +1
    public RecuperaSenha(InformacaoRede informacaoRede, Cartao cartao) {
        this.instante = LocalDateTime.now();
        this.ipAddressCliente = informacaoRede.getIpAddress();
        this.userAgent = informacaoRede.getUserAgent();
        this.cartao = cartao;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getInstante() {
        return instante;
    }

    public String getIpAddressCliente() {
        return ipAddressCliente;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public Cartao getCartao() {
        return cartao;
    }

    @Override
    public String toString() {
        return "RecuperaSenha{" +
                "instante=" + instante +
                ", ipAddressCliente='" + ipAddressCliente + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", cartao=" + cartao +
                '}';
    }
}
