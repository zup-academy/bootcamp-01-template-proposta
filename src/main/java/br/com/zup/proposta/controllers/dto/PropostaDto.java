package br.com.zup.proposta.controllers.dto;

public class PropostaDto {
    
    private String id;

    @Deprecated
    public PropostaDto(){}

    public PropostaDto (String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

}
