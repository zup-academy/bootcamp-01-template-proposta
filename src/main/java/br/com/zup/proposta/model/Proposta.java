package br.com.zup.proposta.model;


import br.com.zup.proposta.dto.AvaliacaoPropostaRequest;
import br.com.zup.proposta.model.enums.StatusAvaliacaoProposta;
import br.com.zup.proposta.util.CodificarInformacoes;
import br.com.zup.proposta.validations.CpfCnpj;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NotBlank
    private String documento;
    @NotBlank @Email
    private String email;
    @NotBlank
    private String nome;
    @NotBlank
    private String endereco;
    @NotNull
    @Positive
    private BigDecimal salarioBruto;
    @Enumerated(EnumType.STRING)
    private StatusAvaliacaoProposta statusAvaliacaoProposta;

    @OneToOne(mappedBy = "proposta", cascade = CascadeType.MERGE)
    private Cartao cartao;

    @Transient
    private TextEncryptor codificador;

    @Deprecated
    public Proposta(){}

    public Proposta(@NotBlank String documento, @NotBlank @Email String email, @NotBlank String nome,
                    @NotBlank String endereco, @NotNull @Positive BigDecimal salarioBruto) {

        obterCodificador();

        this.documento = codificador.encrypt(documento);
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salarioBruto = salarioBruto;
        this.statusAvaliacaoProposta = StatusAvaliacaoProposta.PENDENTE;
    }

    @JsonIgnore
    public UUID getId() {
        return id;
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

    public BigDecimal getSalarioBruto() {
        return salarioBruto;
    }

    public String getDocumento() {
        return documento;
    }

    public StatusAvaliacaoProposta getStatusAvaliacaoProposta() {
        return statusAvaliacaoProposta;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void atualizaStatus(StatusAvaliacaoProposta statusAvaliacaoProposta){
        this.statusAvaliacaoProposta = statusAvaliacaoProposta;
    }

    public void obterCodificador(){
        String salt = KeyGenerators.string().generateKey();
        this.codificador = Encryptors.text("password", salt);
    }

    public AvaliacaoPropostaRequest toAvaliacaoPropostaRequest(){
        String documentoLimpo = codificador.decrypt(this.documento);
        return new AvaliacaoPropostaRequest(documentoLimpo, this.nome, this.id);
    }

    public void associarCartao(String numeroCartao) {
        this.cartao = new Cartao(numeroCartao, this);
    }

    @Override
    public String toString() {
        return "Proposta{" +
                "documento='" + documento + '\'' +
                ", email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", endereco='" + endereco + '\'' +
                ", salarioBruto=" + salarioBruto +
                ", statusAvaliacaoProposta=" + statusAvaliacaoProposta +
                '}';
    }

}
