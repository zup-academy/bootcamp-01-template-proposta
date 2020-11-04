package br.com.itau.cartaobrancoproposta.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Embeddable
public class Cartao {

    @JsonProperty(value = "id")
    private String idCartao;
    private String emitidoEm;
    private String titular;
    @ElementCollection
    private List<Bloqueio> bloqueios;
    @ElementCollection
    private List<AvisoViagem> avisos;
    @ElementCollection
    private List<CarteiraDigital> carteiras;
    @ElementCollection
    private List<Parcela> parcelas;
    private BigDecimal limite;
    @Embedded
    private Renegociacao renegociacao;
    @Embedded
    private Vencimento vencimento;
    private String idProposta;

    @Deprecated
    public Cartao() {
    }

    public Cartao(String emitidoEm, String titular, List<Bloqueio> bloqueios, List<AvisoViagem> avisos, List<CarteiraDigital> carteiras, List<Parcela> parcelas, BigDecimal limite, Renegociacao renegociacao, Vencimento vencimento, String idProposta) {
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

    public String getIdCartao() {
        return idCartao;
    }

    public void setIdCartao(String idCartao) {
        this.idCartao = idCartao;
    }

    public String getEmitidoEm() {
        return emitidoEm;
    }

    public void setEmitidoEm(String emitidoEm) {
        this.emitidoEm = emitidoEm;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public List<Bloqueio> getBloqueios() {
        return bloqueios;
    }

    public void setBloqueios(List<Bloqueio> bloqueios) {
        this.bloqueios = bloqueios;
    }

    public List<AvisoViagem> getAvisos() {
        return avisos;
    }

    public void setAvisos(List<AvisoViagem> avisos) {
        this.avisos = avisos;
    }

    public List<CarteiraDigital> getCarteiras() {
        return carteiras;
    }

    public void setCarteiras(List<CarteiraDigital> carteiras) {
        this.carteiras = carteiras;
    }

    public List<Parcela> getParcelas() {
        return parcelas;
    }

    public void setParcelas(List<Parcela> parcelas) {
        this.parcelas = parcelas;
    }

    public BigDecimal getLimite() {
        return limite;
    }

    public void setLimite(BigDecimal limite) {
        this.limite = limite;
    }

    public Renegociacao getRenegociacao() {
        return renegociacao;
    }

    public void setRenegociacao(Renegociacao renegociacao) {
        this.renegociacao = renegociacao;
    }

    public Vencimento getVencimento() {
        return vencimento;
    }

    public void setVencimento(Vencimento vencimento) {
        this.vencimento = vencimento;
    }

    public String getIdProposta() {
        return idProposta;
    }

    public void setIdProposta(String idProposta) {
        this.idProposta = idProposta;
    }
}
