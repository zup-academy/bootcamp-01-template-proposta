package br.com.proposta.models;

import br.com.proposta.models.Enums.StatusAvaliacaoProposta;
import br.com.proposta.repositories.PropostaRepository;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
