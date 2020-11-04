package br.com.cartao.proposta.domain.model;

import br.com.cartao.proposta.annotation.CpfOuCnpj;
import br.com.cartao.proposta.domain.enums.EstadoProposta;
import br.com.cartao.proposta.domain.request.AnalisePropostaRequest;
import br.com.cartao.proposta.domain.response.AnalisePropostaResponse;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotBlank @CpfOuCnpj @Column(unique = true)
    private String documento;
    @NotBlank @Email
    private String email;
    @NotBlank
    private String endereco;
    @NotBlank
    private String nome;
    @Positive @NotNull
    private BigDecimal salario;
    @Enumerated(EnumType.STRING)
    // +1
    private EstadoProposta estadoProposta;

    private Boolean cartaoCriado;

    @OneToOne(mappedBy = "proposta", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Cartao cartao;

    @Deprecated
    public Proposta() {
    }

    public Proposta(@NotBlank String documento, @NotBlank String email, @NotBlank String endereco, @NotBlank String nome, @Positive @NotBlank BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.endereco = endereco;
        this.nome = nome;
        this.salario = salario;
        this.estadoProposta = EstadoProposta.PENDENTE;
        this.cartaoCriado = Boolean.FALSE;
    }

    public String getId() {
        return id;
    }

    public String getDocumento() {
        return documento;
    }

    public String getEmail() {
        return email;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public EstadoProposta getEstadoProposta() {
        return estadoProposta;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Proposta{" +
                "documento='" + documento + '\'' +
                ", email='" + email + '\'' +
                ", endereco='" + endereco + '\'' +
                ", nome='" + nome + '\'' +
                ", salario=" + salario +
                '}';
    }

    public AnalisePropostaRequest toAnalisePropostaRequest(){
        return new AnalisePropostaRequest(this.documento,this.nome,this.id);
    }

    public void adicionaEstadoProposta(AnalisePropostaResponse analisePropostaResponse) {
        EstadoProposta estadoProposta = analisePropostaResponse.getResultadoSolicitacao().toEstadoProposta();

        this.estadoProposta = estadoProposta;

    }

    public void alteraStatusCartaoCriado(boolean cartaoCriado) {
        if (cartaoCriado){
            this.cartaoCriado = cartaoCriado;
        }
    }

    public void adicionaNumeroCartao(Cartao cartao){
        this.cartao = cartao;
    }
}
