package br.com.zup.bootcamp.proposta.api.dto;

import br.com.zup.bootcamp.proposta.domain.entity.Proposta;
import br.com.zup.bootcamp.proposta.domain.service.enums.StatusProposta;

public class ResponsePropostaDto {

    private final String nome;
    private final String email;
    private final StatusProposta status;

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public StatusProposta getStatus() {
        return status;
    }

    public ResponsePropostaDto(String nome, String email, StatusProposta status) {
        this.nome = nome;
        this.email = email;
        this.status = status;
    }

    public static ResponsePropostaDto toDto(Proposta proposta) {
        return new ResponsePropostaDto(proposta.getNome(), proposta.getEmail(), proposta.getStatus());
    }
}
