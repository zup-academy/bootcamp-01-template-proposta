package br.com.proposta.entidades;

import br.com.proposta.entidades.Enums.StatusAvaliacaoProposta;
import br.com.proposta.repositories.PropostaRepository;
import br.com.proposta.validacoes.interfaces.CpfCnpj;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "proposta")
public class Proposta {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotBlank
    private String nome;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String endereco;

    @NotNull
    @Positive
    private BigDecimal salario;

    @NotBlank
    @CpfCnpj
    private String identificacao;

    private StatusAvaliacaoProposta status;

    @OneToOne
    private Cartao cartao;

    private String numeroCartao;

    @Deprecated
    public Proposta(){}

    public Proposta(@NotBlank String nome, @NotBlank @Email String email, @NotBlank String endereco, @NotNull @Positive BigDecimal salario,
                    @NotBlank String identificacao) {
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
        this.salario = salario;
        this.identificacao = identificacao;
    }



    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void associaCartao(String numeroCartao){
        this.numeroCartao = numeroCartao;
    }

    public boolean ehUnica(PropostaRepository propostaRepository){

        return propostaRepository.findByIdentificacao(this.identificacao).isEmpty();

    }

    public void atualizaStatusElegibilidade(StatusAvaliacaoProposta status){

        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public BigDecimal getSalario() {
        return salario;
    }


    public String getIdentificacao() {
        return identificacao;
    }


    public StatusAvaliacaoProposta getStatus() {
        return status;
    }


    public Cartao getCartao() {
        return cartao;
    }
}
