package br.com.cartao.proposta.domain.model;

import br.com.cartao.proposta.domain.response.*;
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

/*@Entity
@Table(name = "cartao")*/
public class Cartao {

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
    private List<AvisoViagemResponseDto> avisos= new ArrayList<>();
    private List<CarteiraDigitalResponseDto> carteiras = new ArrayList<>();
    private List<ParcelaResponseDto> parcelas= new ArrayList<>();
    @NotNull
    private BigDecimal limite;
    private RenegociacaoResponseDto renegociacao;
    @NotNull
    private VencimentoResponseDto vencimento;
    @NotBlank
    private String idProposta;

    @Deprecated
    public Cartao() {
    }

    public Cartao(@NotBlank String id, String emitidoEm, @NotBlank String titular, List<BloqueioResponseDto> bloqueios, List<AvisoViagemResponseDto> avisos, List<CarteiraDigitalResponseDto> carteiras, List<ParcelaResponseDto> parcelas, @NotNull BigDecimal limite, RenegociacaoResponseDto renegociacao, @NotNull VencimentoResponseDto vencimento, @NotBlank String idProposta) {
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

    public List<AvisoViagemResponseDto> getAvisos() {
        return avisos;
    }

    public List<CarteiraDigitalResponseDto> getCarteiras() {
        return carteiras;
    }

    public List<ParcelaResponseDto> getParcelas() {
        return parcelas;
    }

    public BigDecimal getLimite() {
        return limite;
    }

    public RenegociacaoResponseDto getRenegociacao() {
        return renegociacao;
    }

    public VencimentoResponseDto getVencimento() {
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

    public void setAvisos(List<AvisoViagemResponseDto> avisos) {
        this.avisos = avisos;
    }

    public void setCarteiras(List<CarteiraDigitalResponseDto> carteiras) {
        this.carteiras = carteiras;
    }

    public void setParcelas(List<ParcelaResponseDto> parcelas) {
        this.parcelas = parcelas;
    }

    public void setLimite(BigDecimal limite) {
        this.limite = limite;
    }

    public void setRenegociacao(RenegociacaoResponseDto renegociacao) {
        this.renegociacao = renegociacao;
    }

    public void setVencimento(VencimentoResponseDto vencimento) {
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
