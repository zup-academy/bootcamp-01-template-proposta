package dev.arielalvesdutra.proposta.http_clients.dtos;

import dev.arielalvesdutra.proposta.entities.Cartao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * DTO do Cartão retornado pelo Serviço de Cartões.
 */
public class CartaoRetornoDTO {

    /**
     * ID do cartão gerado no legado.
     *
     * Não pode ser utilizado para APIs públicas.
     */
    private String id;
    private String idProposta;
    private String titular;
    private BigDecimal limite;
    private LocalDateTime emitidoEm;

    public CartaoRetornoDTO() {
    }

    public String getId() {
        return id;
    }

    public CartaoRetornoDTO setId(String id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public CartaoRetornoDTO setEmitidoEm(LocalDateTime emitidoEm) {
        this.emitidoEm = emitidoEm;
        return this;
    }

    public String getTitular() {
        return titular;
    }

    public CartaoRetornoDTO setTitular(String titular) {
        this.titular = titular;
        return this;
    }

    public String getIdProposta() {
        return idProposta;
    }

    public CartaoRetornoDTO setIdProposta(String idProposta) {
        this.idProposta = idProposta;
        return this;
    }

    public BigDecimal getLimite() {
        return limite;
    }

    public CartaoRetornoDTO setLimite(BigDecimal limite) {
        this.limite = limite;
        return this;
    }

    public Cartao paraEntidade() {
        return new Cartao()
                .setLegadoId(id)
                .setEmitidoEm(emitidoEm.atOffset(ZoneOffset.UTC))
                .setTitular(titular)
                .setLimite(limite);
    }
}
