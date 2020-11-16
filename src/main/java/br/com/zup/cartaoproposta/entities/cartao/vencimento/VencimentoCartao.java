package br.com.zup.cartaoproposta.entities.cartao.vencimento;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

/**
 * Contagem de carga intrínseca da classe: 0
 */

@Embeddable
public class VencimentoCartao {

    private String id;
    private int dia;
    private LocalDateTime dataDeCriacao;

    @Deprecated
    public VencimentoCartao(){}

    public VencimentoCartao(String id, int dia, LocalDateTime dataDeCriacao) {
        this.id = id;
        this.dia = dia;
        this.dataDeCriacao = dataDeCriacao;
    }

    public String getId() {
        return id;
    }

    public int getDia() {
        return dia;
    }

    public LocalDateTime getDataDeCriacao() {
        return dataDeCriacao;
    }
}
