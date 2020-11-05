package dev.arielalvesdutra.proposta.http_clients.dtos;

import dev.arielalvesdutra.proposta.entities.Proposta;

/**
 * DTO para solicitação de analise de dados financeiros do solicitante.
 *
 * Será verificado se o solicitante possui restriçõees financeiras.
 */
public class SolicitacaoAnaliseDTO {

    /**
     * ID da proposta.
     */
    private String idProposta;
    /**
     * Nome do solicitante.
     */
    private String nome;
    /**
     * Documento do solicitante.
     */
    private String documento;

    public SolicitacaoAnaliseDTO() {
    }

    public SolicitacaoAnaliseDTO(Proposta proposta) {
        documento = proposta.getDocumento();
        nome = proposta.getNome();
        idProposta = proposta.getId();
    }

    public String getIdProposta() {
        return idProposta;
    }

    public SolicitacaoAnaliseDTO setIdProposta(String idProposta) {
        this.idProposta = idProposta;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public SolicitacaoAnaliseDTO setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public String getDocumento() {
        return documento;
    }

    public SolicitacaoAnaliseDTO setDocumento(String documento) {
        this.documento = documento;
        return this;
    }
}
