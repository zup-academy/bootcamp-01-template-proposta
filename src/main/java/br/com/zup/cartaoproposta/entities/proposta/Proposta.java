package br.com.zup.cartaoproposta.entities.proposta;

import br.com.zup.cartaoproposta.entities.analisesolicitante.ResultadoSolicitacao;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * Contagem de carga intrínseca da classe: 5
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

    @Deprecated
    public Proposta() {}

    public Proposta(@NotBlank String documento, @NotBlank @Email String email, @NotBlank String nome, @NotBlank String endereco, @NotNull @Positive BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
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

    //1
    public void defineStatusProposta(ResultadoSolicitacao resultadoSolicitacao) {
        //1
        switch (resultadoSolicitacao) {
            case SEM_RESTRICAO:
                //1
                if (this.statusProposta != StatusProposta.ELEGIVEL) {
                    this.statusProposta = StatusProposta.AGUARDANDO_CARTAO;
                }
                break;
            case COM_RESTRICAO:
                this.statusProposta = StatusProposta.NAO_ELEGIVEL;
                break;
        }
    }

    public void atualizaStatusProposta() {
        //1
        if (this.statusProposta == StatusProposta.AGUARDANDO_CARTAO) {
            this.statusProposta = StatusProposta.ELEGIVEL;
        }
    }
}
