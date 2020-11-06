package br.com.zup.proposta.dto.response;

import br.com.zup.proposta.enums.StatusAvaliacaoProposta;
import br.com.zup.proposta.model.Proposta;

import java.math.BigDecimal;

public class PropostaResponse {

    private String documento;
    private String email;
    private String nome;
    private String endereco;
    private BigDecimal salario;
    private StatusAvaliacaoProposta statusAvaliacaoProposta;

     public PropostaResponse(Proposta proposta) {
        this.documento = proposta.descriptografarDocumento();
        this.email = proposta.getEmail();
        this.nome = proposta.getNome();
        this.endereco = proposta.getEndereco();
        this.salario = proposta.getSalario();
        this.statusAvaliacaoProposta = proposta.getStatusAvaliacao();
    }



    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
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

    public StatusAvaliacaoProposta getStatusAvaliacaoProposta() {
        return statusAvaliacaoProposta;
    }
}
