package com.github.marcoscoutozup.proposta.cartao;

import com.github.marcoscoutozup.proposta.cartao.aviso.Aviso;
import com.github.marcoscoutozup.proposta.cartao.bloqueio.Bloqueio;
import com.github.marcoscoutozup.proposta.cartao.carteira.Carteira;
import com.github.marcoscoutozup.proposta.cartao.parcela.Parcela;
import com.github.marcoscoutozup.proposta.cartao.vencimento.Vencimento;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class Cartao {

    @Id
    private UUID id;

    private LocalDateTime emitidoEm;

    @ElementCollection
    private List<Bloqueio> bloqueios;

    @ElementCollection
    private List<Aviso> avisos;

    @ElementCollection
    private List<Carteira> carteiras;

    @ElementCollection
    private List<Parcela> parcelas;
    private BigDecimal limite;

    @Embedded
    private Vencimento vencimento;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public void setEmitidoEm(LocalDateTime emitidoEm) {
        this.emitidoEm = emitidoEm;
    }

    public List<Bloqueio> getBloqueios() {
        return bloqueios;
    }

    public void setBloqueios(List<Bloqueio> bloqueios) {
        this.bloqueios = bloqueios;
    }

    public List<Aviso> getAvisos() {
        return avisos;
    }

    public void setAvisos(List<Aviso> avisos) {
        this.avisos = avisos;
    }

    public List<Carteira> getCarteiras() {
        return carteiras;
    }

    public void setCarteiras(List<Carteira> carteiras) {
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

    public Vencimento getVencimento() {
        return vencimento;
    }

    public void setVencimento(Vencimento vencimento) {
        this.vencimento = vencimento;
    }

    @Override
    public String toString() {
        return "Cartao{" +
                "id=" + id +
                ", emitidoEm=" + emitidoEm +
                ", bloqueios=" + bloqueios +
                ", avisos=" + avisos +
                ", carteiras=" + carteiras +
                ", parcelas=" + parcelas +
                ", limite=" + limite +
                ", vencimento=" + vencimento +
                '}';
    }
}
