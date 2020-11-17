package br.com.zup.proposta.controllers.apiResponses.cartao;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import br.com.zup.proposta.model.Proposta;
import br.com.zup.proposta.model.cartao.Cartao;

public class CartaoResponse {
    
    private String id;
    private LocalDateTime emitidoEm;
    private String titular;

    private Set<CartaoBloqueioResponse> bloqueios = new HashSet<>();
    private Set<CartaoAvisosResponse> avisos = new HashSet<>();
    private Set<CarteiraDigitalResponse> carteiras = new HashSet<>();
    private Set<ParcelaResponse> parcelas = new HashSet<>();

    private Integer limite;

    private CartaoRenegociacaoResponse renegociacao;
    private CartaoVencimentoResponse vencimento;

    private String idProposta;

    public CartaoResponse(String id, LocalDateTime emitidoEm, String titular, Set<CartaoBloqueioResponse> bloqueios, 
        Set<CartaoAvisosResponse> avisos, Set<CarteiraDigitalResponse> carteiras, Set<ParcelaResponse> parcelas, 
        Integer limite, CartaoRenegociacaoResponse renegociacao, CartaoVencimentoResponse vencimento, String idProposta) {
        this.id = id;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.bloqueios = bloqueios;
        this.avisos = avisos;
        this.carteiras = carteiras;
        this.parcelas = parcelas;
        this.limite = limite;
        this.renegociacao = renegociacao;
        this.vencimento = vencimento;
        this.idProposta = idProposta;
    }

    public String getIdProposta() {
        return this.idProposta;
    }

    public String getId() {
        return this.id;
    }

    public LocalDateTime getEmitidoEm() {
        return this.emitidoEm;
    }

    public String getTitular() {
        return this.titular;
    }

    public Set<CartaoBloqueioResponse> getBloqueios() {
        return this.bloqueios;
    }

    public Set<CartaoAvisosResponse> getAvisos() {
        return this.avisos;
    }

    public Set<CarteiraDigitalResponse> getCarteiras() {
        return this.carteiras;
    }

    public Set<ParcelaResponse> getParcelas() {
        return this.parcelas;
    }

    public Integer getLimite() {
        return this.limite;
    }

    public CartaoRenegociacaoResponse getRenegociacao() {
        return this.renegociacao;
    }

    public CartaoVencimentoResponse getVencimento() {
        return this.vencimento;
    }

    public Cartao toCartao(Proposta proposta) {
        return new Cartao(this.id, this.emitidoEm, this.titular, this.bloqueios, this.avisos, 
            this.carteiras, this.parcelas, this.limite, this.renegociacao, 
            this.vencimento, proposta);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", emitidoEm='" + getEmitidoEm() + "'" +
            ", titular='" + getTitular() + "'" +
            ", bloqueios='" + getBloqueios() + "'" +
            ", avisos='" + getAvisos() + "'" +
            ", carteiras='" + getCarteiras() + "'" +
            ", parcelas='" + getParcelas() + "'" +
            ", limite='" + getLimite() + "'" +
            ", renegociacao='" + getRenegociacao() + "'" +
            ", vencimento='" + getVencimento() + "'" +
            ", idProposta='" + getIdProposta() + "'" +
            "}";
    }

}
