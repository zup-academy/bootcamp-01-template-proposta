package br.com.zup.proposta.controllers.dto;

import br.com.zup.proposta.model.enums.EstadoProposta;

public class DetailedPropostaDto {
    
    private String id;
    private String nome;
    private EstadoProposta estadoProposta;
    private boolean cartaoCriado;

    public DetailedPropostaDto(String id, String nome, EstadoProposta estadoProposta, boolean cartaoCriado) {
        this.id = id;
        this.nome = nome;
        this.estadoProposta = estadoProposta;
        this.cartaoCriado = cartaoCriado;
    }

    public String getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public EstadoProposta getEstadoProposta() {
        return this.estadoProposta;
    }

    public boolean getCartaoCriado() {
        return this.cartaoCriado;
    }

    public boolean isCartaoCriado() {
        return this.cartaoCriado;
    }
    
}
