package com.proposta.criacaocartao;

import com.proposta.bloqueiodecartao.Bloqueios;
import com.proposta.bloqueiodecartao.StatusCartao;
import com.proposta.cadastraravisoviagem.AvisoViagem;
import com.proposta.criacaobiometria.Biometria;
import com.proposta.solicitacaoderecuperacaodesenha.RecuperarSenha;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Cartao {

    @Id
    @NotBlank
    private String id;

    @NotNull
    private LocalDateTime emitidoEm;

    @NotBlank
    private String titular;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Bloqueios> bloqueios;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<AvisoViagem> avisos;

    @ElementCollection
    private Set<Carteiras> carteiras;

    @ElementCollection
    private Set<Parcelas> parcelas;

    private BigDecimal limite;

    @ElementCollection
    private Set<Renegociacao> renegociacao;

    @ElementCollection
    private Set<Vencimento> vencimento;

    private String idProposta;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Biometria> biometrias;

    private StatusCartao status = StatusCartao.DESBLOQUEADO;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<RecuperarSenha> recuperarSenha;

    @Deprecated
    public Cartao() {}

    public Cartao(String id, @NotBlank LocalDateTime emitidoEm, @NotBlank String titular,
                  Set<Bloqueios> bloqueios, Set<AvisoViagem> avisos, Set<Carteiras> carteiras,
                  Set<Parcelas> parcelas, BigDecimal limite, Set<Renegociacao> renegociacao,
                  Set<Vencimento> vencimento, String idProposta) {
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

    public void ativarBloqueio(Bloqueios bloqueio) {
        bloqueios.add(bloqueio);
    }

    public void recuperarSenha(RecuperarSenha solicitaRecuperacao) {
        recuperarSenha.add(solicitaRecuperacao);
    }

    public void adicionarAvisos(AvisoViagem avisoViagem) {
        avisos.add(avisoViagem);
    }

    public void adicionarBiometria(Biometria novaBiometria) {
        biometrias.add(novaBiometria);
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

    public Set<AvisoViagem> getAvisos() {
        return avisos;
    }

    public void setAvisos(Set<AvisoViagem> avisos) {
        this.avisos = avisos;
    }

    public Set<RecuperarSenha> getRecuperarSenha() {
        return recuperarSenha;
    }

    public void setRecuperarSenha(Set<RecuperarSenha> recuperarSenha) {
        this.recuperarSenha = recuperarSenha;
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

    public Set<Renegociacao> getRenegociacao() {
        return renegociacao;
    }

    public void setRenegociacao(Set<Renegociacao> renegociacao) {
        this.renegociacao = renegociacao;
    }

    public Set<Vencimento> getVencimento() {
        return vencimento;
    }

    public void setVencimento(Set<Vencimento> vencimento) {
        this.vencimento = vencimento;
    }

    public String getIdProposta() {
        return idProposta;
    }

    public void setIdProposta(String idProposta) {
        this.idProposta = idProposta;
    }

    public Set<Biometria> getBiometrias() {
        return biometrias;
    }

    public void setBiometrias(Set<Biometria> biometrias) {
        this.biometrias = biometrias;
    }

    public StatusCartao getStatus() {
        return status;
    }

    public void setStatus(StatusCartao status) {
        this.status = status;
    }

}
