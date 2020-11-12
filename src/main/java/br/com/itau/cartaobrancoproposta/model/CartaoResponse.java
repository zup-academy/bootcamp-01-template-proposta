package br.com.itau.cartaobrancoproposta.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class CartaoResponse {

    private final String numeroCartao;
    private final String emitidoEm;
    private final String titular;
    @JsonProperty(value = "bloqueios")
    private final List<BloqueioResponse> bloqueiosResponse;
    @JsonProperty(value = "avisos")
    private final List<AvisoViagemResponse> avisosResponse;
    @JsonProperty(value = "carteiras")
    private final List<CarteiraDigitalResponse> carteirasResponse;
    private final List<Parcela> parcelas;
    private final BigDecimal limite;
    private final Renegociacao renegociacao;
    private final Vencimento vencimento;
    @JsonProperty(value = "biometrias")
    private final List<BiometriaResponse> biometriaResponse;
    private final Recuperacao recuperacao;

    public CartaoResponse(String numeroCartao, String emitidoEm, String titular, List<BloqueioResponse> bloqueiosResponse,
                          List<AvisoViagemResponse> avisosResponse, List<CarteiraDigitalResponse> carteirasResponse, List<Parcela> parcelas,
                          BigDecimal limite, Renegociacao renegociacao, Vencimento vencimento, List<BiometriaResponse> biometriaResponse, Recuperacao recuperacao) {
        this.numeroCartao = numeroCartao;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.bloqueiosResponse = bloqueiosResponse;
        this.avisosResponse = avisosResponse;
        this.carteirasResponse = carteirasResponse;
        this.parcelas = parcelas;
        this.limite = limite;
        this.renegociacao = renegociacao;
        this.vencimento = vencimento;
        this.biometriaResponse = biometriaResponse;
        this.recuperacao = recuperacao;
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

    public List<BloqueioResponse> getBloqueiosResponse() {
        return bloqueiosResponse;
    }

    public List<AvisoViagemResponse> getAvisosResponse() {
        return avisosResponse;
    }

    public List<CarteiraDigitalResponse> getCarteirasResponse() {
        return carteirasResponse;
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

    public List<BiometriaResponse> getBiometriaResponse() {
        return biometriaResponse;
    }

    public Recuperacao getRecuperacao() {
        return recuperacao;
    }

    public CartaoResponse(Cartao cartao) {
        this.numeroCartao = cartao.getNumeroCartao();
        this.emitidoEm = cartao.getEmitidoEm();
        this.titular = cartao.getTitular();
        this.bloqueiosResponse = cartao.getBloqueios().stream().map(bloqueio -> new BloqueioResponse(bloqueio.getLocalDateTime(), bloqueio.getEstadoBloqueio())).collect(Collectors.toList());
        this.avisosResponse = cartao.getAvisos().stream().map(avisoViagem -> new AvisoViagemResponse(avisoViagem.getValidoAte(), avisoViagem.getDestino())).collect(Collectors.toList());
        this.carteirasResponse = cartao.getCarteiras().stream().map(carteiraDigital -> new CarteiraDigitalResponse(carteiraDigital.getEmissor(), carteiraDigital.getAssociadaEm())).collect(Collectors.toList());
        this.parcelas = cartao.getParcelas();
        this.limite = cartao.getLimite();
        this.renegociacao = cartao.getRenegociacao();
        this.vencimento = cartao.getVencimento();
        this.biometriaResponse = cartao.getBiometrias().stream().map(BiometriaResponse::new).collect(Collectors.toList());
        this.recuperacao = cartao.getRecuperacao();
    }
}