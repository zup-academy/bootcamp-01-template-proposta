package com.github.marcoscoutozup.proposta.proposta;

import com.github.marcoscoutozup.proposta.cartao.Cartao;
import com.github.marcoscoutozup.proposta.proposta.enums.StatusDaProposta;
import com.github.marcoscoutozup.proposta.validator.cpfoucnpj.CpfOuCnpj;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@NamedQuery(name = "findPropostaByDocumento", query = "select p from Proposta p where p.documento = :documento")
@NamedQuery(name = "findPropostaByStatus", query = "select p from Proposta p where p.statusDaProposta = :statusDaProposta")
public class Proposta {

    @Id
    @GeneratedValue(generator = "uuid4")
    private UUID id;

    @NotBlank
    private String documento;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String nome;

    @NotBlank
    private String endereco;

    @NotNull
    private BigDecimal salario;

    @Enumerated(EnumType.STRING)
    private StatusDaProposta statusDaProposta;

    @OneToOne
    private Cartao cartao;

    @Deprecated
    public Proposta() {
    }

    public Proposta(@NotBlank String documento, @NotBlank String email, @NotBlank String nome, @NotBlank String endereco, @NotNull BigDecimal salario) {
        this.documento = encriptarDocumento(documento);
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public UUID getId() {
        return id;
    }

    public String getDocumento() {
        return documento;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public StatusDaProposta getStatusDaProposta() {
        return statusDaProposta;
    }

    public String encriptarDocumento(String documento){
        return new BCryptPasswordEncoder().encode(documento);
    }

    public void modificarStatusDaProposta(StatusDaProposta statusDaProposta) {
        Assert.notNull(statusDaProposta, "O status da proposta não pode ser nulo para modificação");
        this.statusDaProposta = statusDaProposta;
    }

    public void incluirCartaoNaProposta(Cartao cartao){
        this.cartao = cartao;
    }

    public boolean verificarSeNaoExisteCartao(){
       return Objects.isNull(cartao);
    }

}
