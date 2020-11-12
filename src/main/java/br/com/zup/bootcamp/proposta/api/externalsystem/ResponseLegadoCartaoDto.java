package br.com.zup.bootcamp.proposta.api.externalsystem;

import br.com.zup.bootcamp.proposta.domain.entity.Cartao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class ResponseLegadoCartaoDto {
    private UUID id;
    private LocalDateTime emitidoEm;
    private String titular;
    private BigDecimal limite;

    public Cartao toEntity(){
        return new Cartao(id, emitidoEm, titular, limite);
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public String getTitular() {
        return titular;
    }

    public BigDecimal getLimite() {
        return limite;
    }
}
