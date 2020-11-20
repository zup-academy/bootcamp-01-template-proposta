package br.com.zup.proposta.dto;


import br.com.zup.proposta.model.AvisoViagem;
import br.com.zup.proposta.model.Cartao;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class NovoAvisoViagemRequest {

    private @NotBlank String destinoViagem;
    @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
    private @Future @NotNull LocalDate dataTerminoViagem;

    @Deprecated
    public NovoAvisoViagemRequest(){}

    public NovoAvisoViagemRequest(@NotBlank String destinoViagem,
                                  @Future @NotNull LocalDate dataTerminoViagem) {
        this.destinoViagem = destinoViagem;
        this.dataTerminoViagem = LocalDate.now();
    }

    public String getDestinoViagem() {
        return destinoViagem;
    }

    public LocalDate getDataTerminoViagem() {
        return dataTerminoViagem;
    }

    public AvisoViagem toAvisoViagem(Cartao cartao, String userAgent, String ipClient) {
        return new AvisoViagem(cartao, this.destinoViagem, this.dataTerminoViagem,
                userAgent, ipClient);
    }

}
