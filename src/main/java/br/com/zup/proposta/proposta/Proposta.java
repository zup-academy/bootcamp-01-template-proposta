package br.com.zup.proposta.proposta;

import br.com.zup.proposta.analiseproposta.StatusAvaliacaoProposta;
import br.com.zup.proposta.cartao.Cartao;
import org.apache.tomcat.util.http.parser.Authorization;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.security.CryptoPrimitive;
import java.util.Objects;

@Entity
public class Proposta {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private @NotBlank String documento;
    private @NotBlank @Email String email;
    private @NotBlank String nome;
    private @NotBlank String endereco;
    private @NotNull @PositiveOrZero BigDecimal salario;
    private @NotNull @Enumerated(EnumType.STRING) StatusAvaliacaoProposta statusAvaliacao;
    private @OneToOne Cartao cartao;

    @Deprecated
    public Proposta() {
    }

    public Proposta(@NotBlank String documento, @NotBlank @Email String email,
                    @NotBlank String nome, @NotBlank String endereco,
                    @NotNull @PositiveOrZero BigDecimal salario) {
        this.documento = criptografarDocumento(documento);
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
        this.statusAvaliacao = StatusAvaliacaoProposta.PENDENTE;
    }

    public StatusAvaliacaoProposta getStatusAvaliacao() {
        return statusAvaliacao;
    }

    public static String criptografarDocumento(String documento) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(documento);
    }

    public void atualizaStatus(StatusAvaliacaoProposta statusAvaliacao) {
        this.statusAvaliacao = statusAvaliacao;
    }

    public void incluirCartao(Cartao cartao) {
        this.cartao = cartao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Proposta proposta = (Proposta) o;
        return Objects.equals(documento, proposta.documento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documento);
    }

    public String getId() {
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
}
