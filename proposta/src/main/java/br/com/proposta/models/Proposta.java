package br.com.proposta.models;

import br.com.proposta.models.Enums.StatusAvaliacaoProposta;
import br.com.proposta.repositories.PropostaRepository;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private String identificacao;

    private StatusAvaliacaoProposta status;

    @OneToOne(mappedBy = "proposta",cascade = CascadeType.MERGE)
    private Cartao cartao;

    @Deprecated
    public Proposta(){}

    public Proposta(@NotBlank String nome, @NotBlank @Email String email, @NotBlank String endereco,
                    @NotNull @Positive BigDecimal salario, @NotBlank String identificacao) {
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
        this.salario = salario;
        this.identificacao = identificacao;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }

    public StatusAvaliacaoProposta getStatus() {
        return status;
    }

    public void setStatus(StatusAvaliacaoProposta status) {
        this.status = status;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }

    public String getIdentificacao() {
        return identificacao;
    }

    public String getNome() {
        return nome;
    }

    public boolean ehUnica(PropostaRepository propostaRepository){

        return propostaRepository.findByIdentificacao(this.identificacao).isEmpty();

    }

    public void atualizaStatusElegibilidade(StatusAvaliacaoProposta status){

        this.status = status;

    }
}
