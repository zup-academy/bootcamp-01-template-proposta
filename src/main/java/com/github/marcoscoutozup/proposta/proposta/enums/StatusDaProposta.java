package com.github.marcoscoutozup.proposta.proposta.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusDaProposta {

    ELEGIVEL("SEM_RESTRICAO"),
    NAO_ELEGIVEL("COM_RESTRICAO");

    String restricao;

    StatusDaProposta(String restricao) {
        this.restricao = restricao;
    }

    @JsonValue
    public String getRestricao() {
        return restricao;
    }
}
