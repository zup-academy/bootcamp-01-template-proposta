package br.com.zup.proposta.model;


import br.com.zup.proposta.model.enums.StatusAvaliacaoProposta;
import br.com.zup.proposta.validations.CpfCnpj;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @NotBlank
    @CpfCnpj(message = "Documento inválido")
    private String documento;
    @NotBlank @Email
    private String email;
    @NotBlank
    private String nome;
    @NotBlank
    private String endereco;
    @NotNull
    @Positive
    private BigDecimal salarioBruto;

    @Enumerated(EnumType.STRING)
    private StatusAvaliacaoProposta statusAvaliacaoProposta; //1

    public Proposta(@NotBlank String documento, @NotBlank @Email String email, @NotBlank String nome,
                    @NotBlank String endereco, @NotNull @Positive BigDecimal salarioBruto) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salarioBruto = salarioBruto;
        this.statusAvaliacaoProposta = StatusAvaliacaoProposta.PENDENTE;
    }

    @JsonIgnore
    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDocumento() {
        return documento;
    }

    public StatusAvaliacaoProposta getStatusAvaliacaoProposta() {
        return statusAvaliacaoProposta;
    }

    public void atualizaStatus(StatusAvaliacaoProposta statusAvaliacaoProposta){
        this.statusAvaliacaoProposta = statusAvaliacaoProposta;
    }

}
