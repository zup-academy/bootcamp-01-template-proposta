package br.com.zup.cartaoproposta.entities.proposta;

import br.com.zup.cartaoproposta.entities.analisesolicitante.ResultadoSolicitacao;
import br.com.zup.cartaoproposta.entities.cartao.Cartao;
import br.com.zup.cartaoproposta.util.AESEncryptionDecryption;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * Contagem de carga intrínseca da classe: 6
 */

@Entity
public class Proposta {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

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
    @Positive
    private BigDecimal salario;
    @Enumerated
    //1
    private StatusProposta statusProposta;

    @OneToOne(mappedBy = "proposta", cascade = CascadeType.MERGE)
    //1
    private Cartao cartao;

    @Deprecated
    public Proposta() {}

    //1
    public Proposta(@NotNull DocumentoCriptografado documento, @NotBlank @Email String email, @NotBlank String nome, @NotBlank String endereco, @NotNull @Positive BigDecimal salario) {
        this.documento = documento.getDocumentoCriptografado();
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public String getId() {
        return id;
    }

    public String getDocumento(String chave) {
        //1
        AESEncryptionDecryption encryptionDecryption = new AESEncryptionDecryption();
        return encryptionDecryption.decrypt(documento, chave);
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

    public StatusProposta getStatusProposta() {
        return statusProposta;
    }

    public Cartao getCartao() {
        return cartao;
    }

    //1
    public void defineStatusProposta(ResultadoSolicitacao resultadoSolicitacao) {
        this.statusProposta = resultadoSolicitacao.normalizacao();
    }

    public void atualizaStatusProposta() {
        //1
        if (this.statusProposta == StatusProposta.AGUARDANDO_CARTAO) {
            this.statusProposta = StatusProposta.ELEGIVEL;
        }
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }
}
