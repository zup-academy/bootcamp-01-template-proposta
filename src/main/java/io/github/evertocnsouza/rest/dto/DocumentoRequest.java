package io.github.evertocnsouza.rest.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.github.evertocnsouza.domain.entity.Proposta;
import io.github.evertocnsouza.validation.CpfCnpj;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class DocumentoRequest {

    @CpfCnpj
    private String documento;

    @Deprecated
    public DocumentoRequest() {

    }

    public DocumentoRequest(Proposta proposta) {
        this.documento = proposta.getDocumento();
    }

}