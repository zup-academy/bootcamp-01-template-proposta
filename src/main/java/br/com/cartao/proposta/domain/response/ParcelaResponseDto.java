package br.com.cartao.proposta.domain.response;

import java.math.BigDecimal;

public class ParcelaResponseDto {

    private String id;
    private Integer quantidade;
    private BigDecimal parcela;

    @Deprecated
    public ParcelaResponseDto() {
    }

    public ParcelaResponseDto(String id, Integer quantidade, BigDecimal parcela) {
        this.id = id;
        this.quantidade = quantidade;
        this.parcela = parcela;
    }

    public String getId() {
        return id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public BigDecimal getParcela() {
        return parcela;
    }

    @Override
    public String toString() {
        return "ParcelaResponseDto{" +
                "id='" + id + '\'' +
                ", quantidade=" + quantidade +
                ", parcela=" + parcela +
                '}';
    }
}
