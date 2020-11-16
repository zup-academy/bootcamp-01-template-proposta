package br.com.zup.cartaoproposta.entities.cartao.vencimento;

import java.time.LocalDateTime;

/**
 * Contagem de carga intrínseca da classe: 0
 */

public class VencimentoCartaoRetornoLegado {
    private String id;
    private int dia;
    private LocalDateTime dataDeCriacao;

    public VencimentoCartaoRetornoLegado(){}

    public VencimentoCartaoRetornoLegado(String id, int dia, LocalDateTime dataDeCriacao) {
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

    //1
    public VencimentoCartao toModel(){
        return new VencimentoCartao(id, dia, dataDeCriacao);
    }
}
