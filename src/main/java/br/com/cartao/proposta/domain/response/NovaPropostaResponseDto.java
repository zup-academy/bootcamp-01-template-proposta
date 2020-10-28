package br.com.cartao.proposta.domain.response;

import br.com.cartao.proposta.domain.enums.EstadoProposta;
import br.com.cartao.proposta.domain.model.Proposta;

import java.math.BigDecimal;

public class NovaPropostaResponseDto {

    private String id;
    private String documento;
    private String email;
    private String endereco;
    private String nome;
    private BigDecimal salario;
    private EstadoProposta estadoProposta;


    public NovaPropostaResponseDto(Proposta proposta) {
        this.id = proposta.getId();
        this.documento= proposta.getDocumento();
        this.email= proposta.getEmail();
        this.endereco= proposta.getEndereco();
        this.nome= proposta.getNome();
        this.salario=proposta.getSalario();
        this.estadoProposta = proposta.getEstadoProposta();
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

    public String getEndereco() {
        return endereco;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    @Override
    public String toString() {
        return "NovaPropostaResponseDto{" +
                "documento='" + documento + '\'' +
                ", email='" + email + '\'' +
                ", endereco='" + endereco + '\'' +
                ", nome='" + nome + '\'' +
                ", salario=" + salario +
                '}';
    }
}
