package br.com.zup.proposta.model.cartao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import br.com.zup.proposta.controllers.apiResponses.cartao.CartaoAvisosResponse;
import br.com.zup.proposta.controllers.apiResponses.cartao.CartaoBloqueioResponse;
import br.com.zup.proposta.controllers.apiResponses.cartao.CartaoRenegociacaoResponse;
import br.com.zup.proposta.controllers.apiResponses.cartao.CartaoVencimentoResponse;
import br.com.zup.proposta.controllers.apiResponses.cartao.CarteiraDigitalResponse;
import br.com.zup.proposta.controllers.apiResponses.cartao.ParcelaResponse;
import br.com.zup.proposta.model.Proposta;

@Entity
public class Cartao {

    @Id
    private String id;

    @NotNull
    private LocalDateTime emitidoEm;
    @NotNull
    private String titular;

    @NotNull
    @OneToMany(mappedBy = "cartao", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Collection<CartaoBloqueio> bloqueios = new ArrayList<>();
    @NotNull
    @OneToMany(mappedBy = "cartao", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Collection<CartaoAvisos> avisos = new ArrayList<>();
    @NotNull
    @OneToMany(mappedBy = "cartao", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Collection<CarteiraDigital> carteiras = new ArrayList<>();
    @NotNull
    @OneToMany(mappedBy = "cartao", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Collection<Parcela> parcelas = new ArrayList<>();

    @NotNull
    private Integer limite;

    @ManyToOne
    private CartaoRenegociacao renegociacao;
    @NotNull
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private CartaoVencimento vencimento;

    @NotNull
    @OneToMany(mappedBy = "cartao", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Collection<Biometria> biometria = new ArrayList<>();

    @NotNull
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Proposta proposta;

    @Deprecated
    public Cartao() {
    }

    public Cartao(String id, LocalDateTime emitidoEm, String titular, Set<CartaoBloqueioResponse> bloqueios,
            Set<CartaoAvisosResponse> avisos, Set<CarteiraDigitalResponse> carteiras, Set<ParcelaResponse> parcelas,
            Integer limite, CartaoRenegociacaoResponse renegociacao, CartaoVencimentoResponse vencimento,
            Proposta proposta) {
        this.id = id;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.bloqueios = bloqueios.stream().map(bloqueio -> bloqueio.toCartaoBloqueio(this))
                .collect(Collectors.toList());
        this.avisos = avisos.stream().map(aviso -> aviso.toCartaoAviso(this)).collect(Collectors.toList());
        this.carteiras = carteiras.stream().map(carteira -> carteira.toCarteiraDigital(this))
                .collect(Collectors.toList());
        this.parcelas = parcelas.stream().map(parcela -> parcela.toParcela(this)).collect(Collectors.toList());
        this.limite = limite;
        this.renegociacao = renegociacao == null ? null : renegociacao.toRenegociacao(this);
        this.vencimento = vencimento.toVencimento(this);
        this.proposta = proposta;
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

    public Collection<CartaoBloqueio> getBloqueios() {
        return this.bloqueios;
    }

    public Collection<CartaoAvisos> getAvisos() {
        return this.avisos;
    }

    public Collection<CarteiraDigital> getCarteiras() {
        return this.carteiras;
    }

    public Collection<Parcela> getParcelas() {
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

    public Collection<Biometria> getBiometria() {
        return this.biometria;
    }

    public void addBiometria(Biometria biometria) {
        this.biometria.add(biometria);
    }

    public Proposta getProposta() {
        return this.proposta;
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", emitidoEm='" + getEmitidoEm() + "'" + ", titular='" + getTitular()
                + "'" + ", bloqueios='" + getBloqueios() + "'" + ", avisos='" + getAvisos() + "'" + ", carteiras='"
                + getCarteiras() + "'" + ", parcelas='" + getParcelas() + "'" + ", limite='" + getLimite() + "'"
                + ", renegociacao='" + getRenegociacao() + "'" + ", vencimento='" + getVencimento() + "'"
                + ", biometria='" + getBiometria() + "'" + ", proposta='" + getProposta() + "'" + "}";
    }

}
