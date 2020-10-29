package br.com.zup.proposta.controllers.apiResponses.cartao;

import java.time.LocalDateTime;
import java.util.Set;

public class CartaoResponse {
    
    private String id;
    private LocalDateTime emitidoEm;
    private String titular;

    private Set<CartaoBloqueio> bloqueios;
    private Set<CartaoAvisos> avisos;
    private Set<CarteiraDigital> carteiras;
    private Set<Parcela> parcelas;

    private Integer limite;

    private CartaoRenegociacao renegociacao;
    private CartaoVencimento vencimento;

    private String idProposta;

    public CartaoResponse(String id, LocalDateTime emitidoEm, String titular, Set<CartaoBloqueio> bloqueios, Set<CartaoAvisos> avisos, Set<CarteiraDigital> carteiras, Set<Parcela> parcelas, Integer limite, CartaoRenegociacao renegociacao, CartaoVencimento vencimento, String idProposta) {
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

    public String getId() {
        return this.id;
    }

    public LocalDateTime getEmitidoEm() {
        return this.emitidoEm;
    }

    public String getTitular() {
        return this.titular;
    }

    public Set<CartaoBloqueio> getBloqueios() {
        return this.bloqueios;
    }

    public Set<CartaoAvisos> getAvisos() {
        return this.avisos;
    }

    public Set<CarteiraDigital> getCarteiras() {
        return this.carteiras;
    }

    public Set<Parcela> getParcelas() {
        return this.parcelas;
    }

    public Integer getLimite() {
        return this.limite;
    }

    public CartaoRenegociacao getRenegociacao() {
        return this.renegociacao;
    }

    public CartaoVencimento getVencimento() {
        return this.vencimento;
    }

    public String getIdProposta() {
        return this.idProposta;
    }

}
