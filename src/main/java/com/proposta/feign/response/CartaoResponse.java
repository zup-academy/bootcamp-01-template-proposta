package com.proposta.feign.response;

import com.proposta.bloqueiodecartao.Bloqueios;
import com.proposta.criacaocartao.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public class CartaoResponse {

    @NotBlank
    private String id;

    @NotNull
    private LocalDateTime emitidoEm;

    @NotBlank
    private String titular;

    private Set<Bloqueios> bloqueios;

    private Set<Avisos> avisos;

    private Set<Carteiras> carteiras;

    private Set<Parcelas> parcelas;

    private BigDecimal limite;

    private Renegociacao renegociacao;

    private Vencimento vencimento;

    private String idProposta;

    public CartaoResponse() {

    }

    public Cartao toModel(){
        Set<Renegociacao> renegociacaoSet = renegociacao != null ? Set.of(renegociacao):null;
        Set<Vencimento> vencimentoSet = vencimento != null ? Set.of(vencimento):null;

        return new Cartao(id, emitidoEm, titular, bloqueios, avisos, carteiras, parcelas, limite,
                renegociacaoSet, vencimentoSet, idProposta);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public void setEmitidoEm(LocalDateTime emitidoEm) {
        this.emitidoEm = emitidoEm;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public Set<Bloqueios> getBloqueios() {
        return bloqueios;
    }

    public void setBloqueios(Set<Bloqueios> bloqueios) {
        this.bloqueios = bloqueios;
    }

    public Set<Avisos> getAvisos() {
        return avisos;
    }

    public void setAvisos(Set<Avisos> avisos) {
        this.avisos = avisos;
    }

    public Set<Carteiras> getCarteiras() {
        return carteiras;
    }

    public void setCarteiras(Set<Carteiras> carteiras) {
        this.carteiras = carteiras;
    }

    public Set<Parcelas> getParcelas() {
        return parcelas;
    }

    public void setParcelas(Set<Parcelas> parcelas) {
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
