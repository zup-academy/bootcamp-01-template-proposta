package com.github.marcoscoutozup.proposta.cartao.vencimento;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class Vencimento {

    private Integer dia;
    private LocalDateTime dataDeCriacao;

    public Integer getDia() {
        return dia;
    }

    public void setDia(Integer dia) {
        this.dia = dia;
    }

    public LocalDateTime getDataDeCriacao() {
        return dataDeCriacao;
    }

    public void setDataDeCriacao(LocalDateTime dataDeCriacao) {
        this.dataDeCriacao = dataDeCriacao;
    }

    @Override
    public String toString() {
        return "Vencimento{" +
                "dia=" + dia +
                ", dataDeCriacao=" + dataDeCriacao +
                '}';
    }
}
