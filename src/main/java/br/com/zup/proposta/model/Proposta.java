package br.com.zup.proposta.model;

import java.nio.charset.Charset;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.bouncycastle.util.encoders.Hex;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import br.com.zup.proposta.controllers.dto.DetailedPropostaDto;
import br.com.zup.proposta.controllers.dto.PropostaDto;
import br.com.zup.proposta.controllers.form.AnaliseRequestForm;
import br.com.zup.proposta.model.cartao.Cartao;
import br.com.zup.proposta.model.enums.EstadoProposta;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotNull
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
    @ManyToOne
    private Cartao cartao;

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
        this.cartao = null;
    }

    public String getId() {
        return this.id;
    }

    public String getDocumento(String pass, String salt) {
        TextEncryptor encryptor = Encryptors.delux(pass,
                new String(Hex.encode(salt.getBytes(Charset.forName("utf-8")))));
        return encryptor.decrypt(this.documento);
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

    public Cartao getCartao() {
        return this.cartao;
    }

    public void setEstadoProposta(EstadoProposta estadoProposta) {
        this.estadoProposta = estadoProposta;
    }
	public void setCartao(Cartao cartao) {
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

    public AnaliseRequestForm toAnaliseForm(String pass, String salt) {
        TextEncryptor encryptor = Encryptors.delux(pass,
                new String(Hex.encode(salt.getBytes(Charset.forName("utf-8")))));
        return new AnaliseRequestForm(encryptor.decrypt(this.documento), this.nome, this.id);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", email='" + getEmail() + "'" +
            ", nome='" + getNome() + "'" +
            ", endereco='" + getEndereco() + "'" +
            ", salario='" + getSalario() + "'" +
            ", estadoProposta='" + getEstadoProposta() + "'" +
            ", cartaoCriado='" + isCartaoCriado() + "'" +
            ", cartao='" + getCartao() + "'" +
            "}";
    }

}
