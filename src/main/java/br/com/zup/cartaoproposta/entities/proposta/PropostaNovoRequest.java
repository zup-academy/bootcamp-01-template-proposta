package br.com.zup.cartaoproposta.entities.proposta;

import br.com.zup.cartaoproposta.validations.cpfcnpj.CpfCnpj;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * Contagem de carga intrínseca da classe: 3
 */

public class PropostaNovoRequest {

    @NotBlank
    //1
    @CpfCnpj
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

    public PropostaNovoRequest(@NotBlank String documento, @NotBlank @Email String email, @NotBlank String nome, @NotBlank String endereco, @NotNull @Positive BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
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

    public String getDocumentoApenasDigitos() {
        return documento.replaceAll("[^0-9]","");
    }

    //1
    public Proposta toModel(String chave){
        //1
        DocumentoCriptografado documentoProposta = new DocumentoCriptografado(this.getDocumentoApenasDigitos(), chave);
        return new Proposta(documentoProposta, email, nome, endereco, salario);
    }


}

