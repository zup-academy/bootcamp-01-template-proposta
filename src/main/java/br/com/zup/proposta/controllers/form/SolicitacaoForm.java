package br.com.zup.proposta.controllers.form;

import java.nio.charset.Charset;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.bouncycastle.util.encoders.Hex;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import br.com.zup.proposta.model.Proposta;
import br.com.zup.proposta.service.validadores.anotações.CpfCnpj;

public class SolicitacaoForm {

    @NotNull
    @CpfCnpj
    private String documento;
    @NotNull
    @Email
    private String email;
    @NotNull
    @NotBlank
    private String nome;
    @NotNull
    @NotBlank
    private String endereco;
    @NotNull
    @Positive
    private Double salario;

    public SolicitacaoForm(@NotNull String documento, @NotNull String email, @NotNull String nome,
            @NotNull String endereco, @NotNull Double salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
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

    public Proposta toProposta(String password, String salt) {
        TextEncryptor encryptor = Encryptors.delux(password,
                new String(Hex.encode(salt.getBytes(Charset.forName("utf-8")))));
        return new Proposta(encryptor.encrypt(this.documento), this.email, this.nome, this.endereco, this.salario);
    }
}
