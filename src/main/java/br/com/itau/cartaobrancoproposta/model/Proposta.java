package br.com.itau.cartaobrancoproposta.model;

import br.com.itau.cartaobrancoproposta.client.CartaoClient;
import br.com.itau.cartaobrancoproposta.validator.CpfOuCnpj;
import feign.FeignException;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotBlank
    @CpfOuCnpj
    private String documento;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String nome;
    @NotBlank
    private String endereco;
    @NotNull
    @Positive
    private BigDecimal salario;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Restricao restricao;
    @Embedded
    private Cartao cartao;

    @Deprecated
    public Proposta() {
    }

    public Proposta(@NotBlank String documento, @NotBlank @Email String email, @NotBlank String nome, @NotBlank String endereco, @NotNull @Positive BigDecimal salario, @NotNull Restricao restricao) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
        this.restricao = restricao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public Restricao getRestricao() {
        return restricao;
    }

    public void setRestricao(Restricao restricao) {
        this.restricao = restricao;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Proposta proposta = (Proposta) o;
        return Objects.equals(id, proposta.id) &&
                Objects.equals(documento, proposta.documento) &&
                Objects.equals(email, proposta.email) &&
                Objects.equals(nome, proposta.nome) &&
                Objects.equals(endereco, proposta.endereco) &&
                Objects.equals(salario, proposta.salario) &&
                restricao == proposta.restricao &&
                Objects.equals(cartao, proposta.cartao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, documento, email, nome, endereco, salario, restricao, cartao);
    }

    public void validaRestricao(String resultadoSolicitacao) {
        if (resultadoSolicitacao.equals("SEM_RESTRICAO")){
            this.restricao = Restricao.ELEGIVEL;
        } else {
            this.restricao = Restricao.NAO_ELEGIVEL;
        }
    }

    public void verificaCartao(CartaoClient cartaoClient) throws FeignException {
        this.cartao = cartaoClient.buscaCartao(this.id);
    }
}
