package br.com.zup.proposta.controllers.form;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.zup.proposta.controllers.apiResponses.cartao.CartaoAvisosResponse;

public class AvisoViagemForm {
    
    @NotNull @NotBlank
    private String destino;
    @NotNull @JsonFormat(pattern = "dd-MM-yyyy") @Future
    private LocalDate dataTermino;

    @Deprecated
    public AvisoViagemForm(){}

    public AvisoViagemForm(String destino, LocalDate dataTermino) {
        this.destino = destino;
        this.dataTermino = dataTermino;
    }

    public String getDestino() {
        return this.destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public LocalDate getDataTermino() {
        return this.dataTermino;
    }

    public void setDataTermino(LocalDate dataTermino) {
        this.dataTermino = dataTermino;
    }

    public CartaoAvisosResponse toCartaoAvisos() {
        return new CartaoAvisosResponse(dataTermino, destino);
    }

}
