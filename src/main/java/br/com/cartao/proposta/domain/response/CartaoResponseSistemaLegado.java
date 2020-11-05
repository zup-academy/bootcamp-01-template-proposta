package br.com.cartao.proposta.domain.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartaoResponseSistemaLegado {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String idCartao;

    @NotBlank
    @JsonProperty(value = "id")
    private String id;
    @JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ss", shape = JsonFormat.Shape.STRING)
    private String emitidoEm;
    @NotBlank
    private String titular;

    private List<BloqueioResponseDto> bloqueios = new ArrayList<>();
    private List<AvisoViagemIntegracaoResponseDto> avisos= new ArrayList<>();
    private List<CarteiraDigitalIntegracaoResponseDto> carteiras = new ArrayList<>();
    private List<ParcelaIntegracaoResponseDto> parcelas= new ArrayList<>();
    @NotNull
    private BigDecimal limite;
    private RenegociacaoIntegracaoResponseDto renegociacao;
    @NotNull
    private VencimentoIntegracaoResponseDto vencimento;
    @NotBlank
    private String idProposta;

    @Deprecated
    public CartaoResponseSistemaLegado() {
    }

    public CartaoResponseSistemaLegado(@NotBlank String id, String emitidoEm, @NotBlank String titular, List<BloqueioResponseDto> bloqueios, List<AvisoViagemIntegracaoResponseDto> avisos, List<CarteiraDigitalIntegracaoResponseDto> carteiras, List<ParcelaIntegracaoResponseDto> parcelas, @NotNull BigDecimal limite, RenegociacaoIntegracaoResponseDto renegociacao, @NotNull VencimentoIntegracaoResponseDto vencimento, @NotBlank String idProposta) {
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

    public List<BloqueioResponseDto> getBloqueios() {
        return bloqueios;
    }

    public List<AvisoViagemIntegracaoResponseDto> getAvisos() {
        return avisos;
    }

    public List<CarteiraDigitalIntegracaoResponseDto> getCarteiras() {
        return carteiras;
    }

    public List<ParcelaIntegracaoResponseDto> getParcelas() {
        return parcelas;
    }

    public BigDecimal getLimite() {
        return limite;
    }

    public RenegociacaoIntegracaoResponseDto getRenegociacao() {
        return renegociacao;
    }

    public VencimentoIntegracaoResponseDto getVencimento() {
        return vencimento;
    }

    public String getIdProposta() {
        return idProposta;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmitidoEm(String emitidoEm) {
        this.emitidoEm = emitidoEm;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public void setBloqueios(List<BloqueioResponseDto> bloqueios) {
        this.bloqueios = bloqueios;
    }

    public void setAvisos(List<AvisoViagemIntegracaoResponseDto> avisos) {
        this.avisos = avisos;
    }

    public void setCarteiras(List<CarteiraDigitalIntegracaoResponseDto> carteiras) {
        this.carteiras = carteiras;
    }

    public void setParcelas(List<ParcelaIntegracaoResponseDto> parcelas) {
        this.parcelas = parcelas;
    }

    public void setLimite(BigDecimal limite) {
        this.limite = limite;
    }

    public void setRenegociacao(RenegociacaoIntegracaoResponseDto renegociacao) {
        this.renegociacao = renegociacao;
    }

    public void setVencimento(VencimentoIntegracaoResponseDto vencimento) {
        this.vencimento = vencimento;
    }

    public void setIdProposta(String idProposta) {
        this.idProposta = idProposta;
    }

    @Override
    public String toString() {
        return "Cartao{" +
                "id='" + id + '\'' +
                ", idCartao='" + idCartao + '\'' +
                ", emitidoEm=" + emitidoEm +
                ", titular='" + titular + '\'' +
                ", bloqueios=" + bloqueios +
                ", avisos=" + avisos +
                ", carteiras=" + carteiras +
                ", parcelas=" + parcelas +
                ", limite=" + limite +
                ", renegociacao=" + renegociacao +
                ", vencimento=" + vencimento +
                ", idProposta='" + idProposta + '\'' +
                '}';
    }
}
