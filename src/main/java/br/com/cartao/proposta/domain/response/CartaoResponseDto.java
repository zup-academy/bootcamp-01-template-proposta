package br.com.cartao.proposta.domain.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CartaoResponseDto {

    private String id;
    private LocalDateTime emitidoEm;
    private String titular;
    private List<BloqueioResponseDto> bloqueios = new ArrayList<>();
    private List<AvisoViagemResponseDto> avisos= new ArrayList<>();
    private List<CarteiraDigitalResponseDto> carteiras = new ArrayList<>();
    private List<ParcelaResponseDto> parcelas= new ArrayList<>();
    private BigDecimal limite;
    private RenegociacaoResponseDto renegociacao;
    private VencimentoResponseDto vencimento;
    private String idProposta;

    public CartaoResponseDto(String id, LocalDateTime emitidoEm, String titular, List<BloqueioResponseDto> bloqueios, List<AvisoViagemResponseDto> avisos, List<CarteiraDigitalResponseDto> carteiras, List<ParcelaResponseDto> parcelas, BigDecimal limite, RenegociacaoResponseDto renegociacao, VencimentoResponseDto vencimento, String idProposta) {
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

    public LocalDateTime getEmitidoEm() {
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

    @Override
    public String toString() {
        return "CartaoResponseDto{" +
                "id='" + id + '\'' +
                ", emitidoEm='" + emitidoEm + '\'' +
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
