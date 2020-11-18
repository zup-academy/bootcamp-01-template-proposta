package br.com.cartao.proposta.domain.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RenegociacaoIntegracaoResponseDto {

    private String id;
    private Integer quantidade;
    private BigDecimal valor;
    private LocalDateTime dataDeCriacao;

    @Deprecated
    public RenegociacaoIntegracaoResponseDto() {
    }

    public RenegociacaoIntegracaoResponseDto(String id, Integer quantidade, BigDecimal valor, LocalDateTime dataDeCriacao) {
        this.id = id;
        this.quantidade = quantidade;
        this.valor = valor;
        this.dataDeCriacao = dataDeCriacao;
    }

    public String getId() {
        return id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDateTime getDataDeCriacao() {
        return dataDeCriacao;
    }

    @Override
    public String toString() {
        return "RenegociacaoResponseDto{" +
                "id='" + id + '\'' +
                ", quantidade=" + quantidade +
                ", valor=" + valor +
                ", dataDeCriacao=" + dataDeCriacao +
                '}';
    }
}
