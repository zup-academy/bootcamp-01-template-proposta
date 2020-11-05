package br.com.itau.cartaobrancoproposta.model;

import java.math.BigDecimal;
import java.util.List;

public class SolicitacaoCartao {

    private final String id;
    private final String emitidoEm;
    private final String titular;
    private final List<Bloqueio> bloqueios;
    private final List<AvisoViagem> avisos;
    private final List<CarteiraDigital> carteiras;
    private final List<Parcela> parcelas;
    private final BigDecimal limite;
    private final Renegociacao renegociacao;
    private final Vencimento vencimento;
    private final String idProposta;

    public SolicitacaoCartao(String id, String emitidoEm, String titular, List<Bloqueio> bloqueios, List<AvisoViagem> avisos, List<CarteiraDigital> carteiras, List<Parcela> parcelas, BigDecimal limite, Renegociacao renegociacao, Vencimento vencimento, String idProposta) {
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
        return id;
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

    public String getIdProposta() {
        return idProposta;
    }

    public Cartao toModel() {
        return new Cartao(this.id, this.emitidoEm, this.titular, this.bloqueios,
                this.avisos, this.carteiras, this.parcelas, this.limite, this.renegociacao,
                this.vencimento);
    }
}