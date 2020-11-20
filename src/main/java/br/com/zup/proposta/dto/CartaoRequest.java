package br.com.zup.proposta.dto;

import br.com.zup.proposta.model.Proposta;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class CartaoRequest {

    private String idProposta;
    @JsonProperty(value = "id")
    private String numero;

    @Deprecated
    public CartaoRequest(){}

    public CartaoRequest(String idProposta) {
        this.idProposta = idProposta;
    }

    public String getNumero() {
        return numero;
    }

    public String getIdProposta() {
        return idProposta;
    }

    @Override
    public String toString() {
        return "CartaoRequest{" +
                "idProposta='" + idProposta + '\'' +
                ", numero='" + numero + '\'' +
                '}';
    }

}
