package br.com.zup.proposta.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.annotations.GenericGenerator;

import br.com.zup.proposta.controllers.dto.PropostaDto;
import br.com.zup.proposta.controllers.form.AnaliseRequestForm;
import br.com.zup.proposta.model.enums.EstadoProposta;
import br.com.zup.proposta.service.validadores.anotações.CpfCnpj;

@Entity
public class Proposta {
    
    @Id 
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotNull @CpfCnpj
    private String documento;
    @NotNull @Email
    private String email;
    @NotNull
    private String nome;
    @NotNull
    private String endereco;
    @NotNull @Positive
    private Double salario;
    @NotNull
    private EstadoProposta estadoProposta;

    @Deprecated
    public Proposta(){}

    public Proposta(String documento, String email, String nome, String endereco, Double salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
        this.estadoProposta = EstadoProposta.NAO_ELEGIVEL;
    }

    public void setEstadoProposta(EstadoProposta estadoProposta) {
        this.estadoProposta = estadoProposta;
    }

	public PropostaDto toDto() {
		return new PropostaDto(this.id);
	}
    
    public AnaliseRequestForm toAnaliseForm() {
        return new AnaliseRequestForm(this.documento, this.nome, this.id);
    }
    
}
