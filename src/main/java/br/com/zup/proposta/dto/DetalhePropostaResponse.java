package br.com.zup.proposta.dto;

import br.com.zup.proposta.model.Cartao;
import br.com.zup.proposta.model.Proposta;
import br.com.zup.proposta.model.enums.StatusAvaliacaoProposta;

import java.math.BigDecimal;
import java.util.Optional;

public class DetalhePropostaResponse {

    private String documento;
    private String email;
    private String nome;
    private String endereco;
    private BigDecimal salarioBruto;
    private StatusAvaliacaoProposta statusAvaliacaoProposta;
    private String numeroCartao;

    @Deprecated
    public DetalhePropostaResponse(){}

    public DetalhePropostaResponse(Proposta proposta) { //1
        this.documento = proposta.getDocumento();
        this.nome = proposta.getNome();
        this.email = proposta.getEmail();
        this.endereco = proposta.getEndereco();
        this.salarioBruto = proposta.getSalarioBruto();
        this.statusAvaliacaoProposta = proposta.getStatusAvaliacaoProposta();
        this.numeroCartao = propostaElegivel(
                Optional.ofNullable(proposta.getCartao()));
    }

    public String getNome() {
        return nome;
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

    public BigDecimal getSalarioBruto() {
        return salarioBruto;
    }

    public StatusAvaliacaoProposta getStatusAvaliacaoProposta() {
        return statusAvaliacaoProposta;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public String propostaElegivel(Optional<Cartao> possivelCartao){ //2
        if (possivelCartao.isEmpty())
            return "";
        return possivelCartao.get().getNumero();
    }

}
