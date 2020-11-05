package br.com.zup.bootcamp.proposta.domain.entity;

import br.com.zup.bootcamp.proposta.api.externalsystem.RequestAvaliacaoFinanceiraDto;
import br.com.zup.bootcamp.proposta.domain.service.StatusProposta;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.scheduling.annotation.Async;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotBlank
    private String documento;
    @Email
    private String email;
    @NotBlank
    private String nome;
    @NotBlank(message = "Obrigatorio")
    private String endereco;
    @Positive
    private BigDecimal salario;

    @Enumerated(EnumType.STRING)
    private StatusProposta status;

    @OneToOne
    private Cartao cartao;

    @Deprecated
    public Proposta (){}

    public Proposta(@NotBlank String documento, @Email String email, @NotBlank String nome, @NotBlank String endereco, @Positive BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
        this.status = StatusProposta.PENDENTE;
    }

    public RequestAvaliacaoFinanceiraDto requestAvaliacaoFinaceira(){
        return new RequestAvaliacaoFinanceiraDto(documento, nome, id);
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

    public StatusProposta getStatus() {
        return status;
    }

    public void setStatus(StatusProposta status) {
        this.status = status;
    }

    public void adicionarCartao(Cartao cartao){
        this.cartao = cartao;
    }

}
