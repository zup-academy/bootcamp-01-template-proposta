package br.com.itau.cartaobrancoproposta.model;

import br.com.itau.cartaobrancoproposta.error.ApiErrorException;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.http.HttpStatus;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Cartao {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotBlank
    private String numeroCartao;
    @NotBlank
    private String emitidoEm;
    @NotBlank
    private String titular;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Bloqueio> bloqueios;
    @OneToMany
    private List<AvisoViagem> avisos;
    @OneToMany
    private List<CarteiraDigital> carteiras;
    @OneToMany(mappedBy = "cartao")
    private List<Parcela> parcelas;
    @NotNull
    @Positive
    private BigDecimal limite;
    @Embedded
    private Renegociacao renegociacao;
    @Embedded
    @NotNull
    private Vencimento vencimento;
    @OneToOne
    private Proposta proposta;
    @OneToMany
    private List<Biometria> biometrias;
    @OneToOne(cascade = CascadeType.ALL)
    private Recuperacao recuperacao;

    @Deprecated
    public Cartao() {
    }

    public Cartao(@NotBlank String numeroCartao, @NotBlank String emitidoEm, @NotBlank String titular,
                  List<Bloqueio> bloqueios, List<AvisoViagem> avisos, List<CarteiraDigital> carteiras,
                  List<Parcela> parcelas, @NotNull BigDecimal limite, Renegociacao renegociacao, @NotNull Vencimento vencimento) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
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

    public List<Biometria> getBiometrias() {
        return biometrias;
    }

    public void setBiometrias(List<Biometria> biometrias) {
        this.biometrias = biometrias;
    }

    public Recuperacao getRecuperacao() {
        return recuperacao;
    }

    public void setRecuperacao(Recuperacao recuperacao) {
        this.recuperacao = recuperacao;
    }

    public void carregaBiometria(Biometria biometria) {
        this.biometrias.add(biometria);
    }

    public void carregaBloqueio(Bloqueio bloqueio) {
        this.bloqueios.add(bloqueio);
    }

    public void carregaRecuperacaoSenha(Recuperacao recuperacao) {
        this.recuperacao = recuperacao;
    }

    public void carregaAvisoViagem(AvisoViagem avisoViagem) {
        this.avisos.add(avisoViagem);
    }

    public void carregaCarteira(CarteiraDigital carteiraDigital) {
        this.carteiras.add(carteiraDigital);
    }

    public void verificaCarteiraJaCadastrada(CarteiraDigital carteiraDigital) {
        if (!this.getCarteiras().isEmpty()) { //1
            this.getCarteiras().forEach(carteira -> { //1
                if (carteira.getEmissor().equals(carteiraDigital.getEmissor())){ //1
                    throw new ApiErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "Carteira digital já cadastrada no sistema"); //1
                }
            });
        }
    }
}
