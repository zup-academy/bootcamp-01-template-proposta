package br.com.cartao.proposta.domain.request;

import br.com.cartao.proposta.domain.model.AvisoViagem;
import br.com.cartao.proposta.domain.model.Cartao;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AvisoViagemRequest {

    @NotBlank
    private final String destinoViagem;
    @NotNull @Future @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private final LocalDate terminaEm;


    public AvisoViagemRequest(@NotBlank String destinoViagem, @NotNull @Future LocalDate terminaEm, String ipAddressCliente, String userAgent) {
        this.destinoViagem = destinoViagem;
        this.terminaEm = terminaEm;
    }

    public AvisoViagem toModel(Cartao cartao, InformacaoRede informacaoRede) {
        return new AvisoViagem(this.destinoViagem, this.terminaEm, informacaoRede.getIpAddress(), informacaoRede.getUserAgent(), cartao);
    }
}
