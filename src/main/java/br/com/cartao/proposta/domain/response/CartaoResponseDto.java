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
    private List<AvisoViagemIntegracaoResponseDto> avisos= new ArrayList<>();
    private List<CarteiraDigitalIntegracaoResponseDto> carteiras = new ArrayList<>();
    private List<ParcelaIntegracaoResponseDto> parcelas= new ArrayList<>();
    private BigDecimal limite;
    private RenegociacaoIntegracaoResponseDto renegociacao;
    private VencimentoIntegracaoResponseDto vencimento;
    private String idProposta;

    public CartaoResponseDto(String id, LocalDateTime emitidoEm, String titular, List<BloqueioResponseDto> bloqueios, List<AvisoViagemIntegracaoResponseDto> avisos, List<CarteiraDigitalIntegracaoResponseDto> carteiras, List<ParcelaIntegracaoResponseDto> parcelas, BigDecimal limite, RenegociacaoIntegracaoResponseDto renegociacao, VencimentoIntegracaoResponseDto vencimento, String idProposta) {
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
