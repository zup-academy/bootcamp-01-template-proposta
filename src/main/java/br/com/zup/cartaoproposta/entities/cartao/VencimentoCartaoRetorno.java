package br.com.zup.cartaoproposta.entities.cartao;

import java.time.LocalDateTime;

/**
 * Contagem de carga intrínseca da classe: 0
 */

public class VencimentoCartaoRetorno {
    private String id;
    private int dia;
    private LocalDateTime dataDeCriacao;

    public VencimentoCartaoRetorno(){}

    public VencimentoCartaoRetorno(String id, int dia, LocalDateTime dataDeCriacao) {
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
    VencimentoCartao toModel(){
        return new VencimentoCartao(id, dia, dataDeCriacao);
    }
}
