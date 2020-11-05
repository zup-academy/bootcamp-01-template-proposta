package br.com.itau.cartaobrancoproposta.model;

import java.math.BigDecimal;
import java.util.List;

public class CartaoResponse {

    private final String numeroCartao;
    private final String emitidoEm;
    private final String titular;
    private final List<Bloqueio> bloqueios;
    private final List<AvisoViagem> avisos;
    private final List<CarteiraDigital> carteiras;
    private final List<Parcela> parcelas;
    private final BigDecimal limite;
    private final Renegociacao renegociacao;
    private final Vencimento vencimento;

    public CartaoResponse(String numeroCartao, String emitidoEm, String titular, List<Bloqueio> bloqueios, List<AvisoViagem> avisos, List<CarteiraDigital> carteiras, List<Parcela> parcelas, BigDecimal limite, Renegociacao renegociacao, Vencimento vencimento) {
        this.numeroCartao = numeroCartao;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.bloqueios = bloqueios;
        this.avisos = avisos;
        this.carteiras = carteiras;
        this.parcelas = parcelas;
        this.limite = limite;
        this.renegociacao = renegociacao;
        this.vencimento = vencimento;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public String getEmitidoEm() {
        return emitidoEm;
    }

    public String getTitular() {
        return titular;
    }

    public List<Bloqueio> getBloqueios() {
        return bloqueios;
    }

    public List<AvisoViagem> getAvisos() {
        return avisos;
    }

    public List<CarteiraDigital> getCarteiras() {
        return carteiras;
    }

    public List<Parcela> getParcelas() {
        return parcelas;
    }

    public BigDecimal getLimite() {
        return limite;
    }

    public Renegociacao getRenegociacao() {
        return renegociacao;
    }

    public Vencimento getVencimento() {
        return vencimento;
    }

    public CartaoResponse(Cartao cartao) {
        this.numeroCartao = cartao.getNumeroCartao();
        this.emitidoEm = cartao.getEmitidoEm();
        this.titular = cartao.getTitular();
        this.bloqueios = cartao.getBloqueios();
        this.avisos = cartao.getAvisos();
        this.carteiras = cartao.getCarteiras();
        this.parcelas = cartao.getParcelas();
        this.limite = cartao.getLimite();
        this.renegociacao = cartao.getRenegociacao();
        this.vencimento = cartao.getVencimento();
    }
}