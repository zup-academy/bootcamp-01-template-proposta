package br.com.zup.proposta.model;

import br.com.zup.proposta.annotations.CpfCnpj;
import br.com.zup.proposta.enums.StatusAvaliacaoProposta;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

@Entity
@NamedQuery(name = "findPropostaByStatus", query = "select p from Proposta p where p.statusAvaliacao = :statusAvaliacao")
public class Proposta {

    @Id
    @GeneratedValue(generator = "uuid4")
    private UUID id;

    //@CpfCnpj
    @NotBlank(message = "campo requerido")
    private String documento;

    @NotBlank(message = "campo requerido") @Email(message = "formato invalido")
    private String email;

    @NotBlank(message = "campo requerido")
    private String nome;

    @NotBlank(message = "campo requerido")
    private String endereco;

    @NotNull(message = "campo requerido")
    @Positive
    private BigDecimal salario;

    @Enumerated(EnumType.STRING)
    private StatusAvaliacaoProposta statusAvaliacao;

    @OneToOne
    private Cartao cartao;

    @Deprecated
    public Proposta() {
    }

    public Proposta(UUID id) {
        this.id = id;
    }

    public Proposta(@NotBlank(message = "campo requerido") String documento, @NotBlank(message = "campo requerido") @Email(message = "formato invalido") String email, @NotBlank(message = "campo requerido") String nome, @NotBlank(message = "campo requerido") String endereco, @NotNull(message = "campo requerido") @Positive BigDecimal salario) {
        this.documento = criptografarDocumento(documento);;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
        this.statusAvaliacao = StatusAvaliacaoProposta.NAO_ELEGIVEL;
    }

    public UUID getId() {
        return id;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public StatusAvaliacaoProposta getStatusAvaliacao() {
        return statusAvaliacao;
    }

    public void atualizaStatus(StatusAvaliacaoProposta statusAvaliacao) {
        Assert.isTrue(this.statusAvaliacao.equals(StatusAvaliacaoProposta.NAO_ELEGIVEL), "uma vez que a proposta é elegível não pode mais trocar");
        this.statusAvaliacao = statusAvaliacao;
    }

    public void incluirCartaoNaProposta(Cartao cartao){
        this.cartao = cartao;
    }

    public boolean verificarSeNaoExisteCartao(){
        return Objects.isNull(cartao);
    }

    public static String criptografarDocumento(String documento){
        return new String(Base64.getEncoder().encode(documento.getBytes()));
    }

    public String descriptografarDocumento(){
        return new String(Base64.getDecoder().decode(documento.getBytes()));
    }
}
