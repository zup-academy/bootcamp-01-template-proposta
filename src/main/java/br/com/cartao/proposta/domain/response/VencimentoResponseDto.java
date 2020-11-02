package br.com.cartao.proposta.domain.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class VencimentoResponseDto {

    private String id;
    private Integer dia;
    @JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ss", shape = JsonFormat.Shape.STRING)
    private String dataDeCriacao;

    @Deprecated
    public VencimentoResponseDto() {
    }

    public VencimentoResponseDto(String id, Integer dia, String dataDeCriacao) {
        this.id = id;
        this.dia = dia;
        this.dataDeCriacao = dataDeCriacao;
    }

    public String getId() {
        return id;
    }

    public Integer getDia() {
        return dia;
    }

    public String getDataDeCriacao() {
        return dataDeCriacao;
    }

    @Override
    public String toString() {
        return "VencimentoResponseDto{" +
                "id='" + id + '\'' +
                ", dia=" + dia +
                ", dataDeCriacao=" + dataDeCriacao +
                '}';
    }
}
