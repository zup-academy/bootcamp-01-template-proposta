package br.com.zup.proposta.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.annotations.GenericGenerator;

import br.com.zup.proposta.controllers.dto.DetailedPropostaDto;
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
    @NotNull
    @CpfCnpj
    private String documento;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String nome;
    @NotNull
    private String endereco;
    @NotNull
    @Positive
    private Double salario;
    @NotNull
    @Enumerated(EnumType.STRING)
    private EstadoProposta estadoProposta;
    @NotNull
    private boolean cartaoCriado;
    @NotNull
    private String cartao;

    @Deprecated
    public Proposta() {
    }

    public Proposta(String documento, String email, String nome, String endereco, Double salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
        this.estadoProposta = EstadoProposta.NAO_ELEGIVEL;
        this.cartaoCriado = false;
        this.cartao = "";
    }

    public String getId() {
        return this.id;
    }

    public String getDocumento() {
        return this.documento;
    }

    public String getEmail() {
        return this.email;
    }

    public String getNome() {
        return this.nome;
    }

    public String getEndereco() {
        return this.endereco;
    }

    public Double getSalario() {
        return this.salario;
    }

    public EstadoProposta getEstadoProposta() {
        return this.estadoProposta;
    }

    public boolean getCartaoCriado() {
        return this.cartaoCriado;
    }

    public String getCartao() {
        return this.cartao;
    }

    public void setEstadoProposta(EstadoProposta estadoProposta) {
        this.estadoProposta = estadoProposta;
    }
	public void setCartao(String cartao) {
        this.cartao = cartao;
    }

    public boolean isCartaoCriado() {
        return this.cartaoCriado;
    }

    public void setCartaoCriado(boolean cartaoCriado) {
        this.cartaoCriado = cartaoCriado;
    }

    public PropostaDto toDto() {
        return new PropostaDto(this.id);
    }

    public DetailedPropostaDto toDetailedDto() {
        return new DetailedPropostaDto(this.id, this.nome, this.estadoProposta, this.cartaoCriado);
    }

    public AnaliseRequestForm toAnaliseForm() {
        return new AnaliseRequestForm(this.documento, this.nome, this.id);
    }

    @Override
    public String toString() {
        return "{" + " id='" + this.id + "'" + ", documento='" + this.documento + "'" + ", email='" + this.email + "'"
                + ", nome='" + this.nome + "'" + ", endereco='" + this.endereco + "'" + ", salario='" + this.salario
                + "'" + ", estadoProposta='" + this.estadoProposta + "'" + ", cartaoCriado='" + this.cartaoCriado + "'"
                + "}";
    }

}
