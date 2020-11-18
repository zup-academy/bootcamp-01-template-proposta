package br.com.cartao.proposta.domain.event;

import br.com.cartao.proposta.domain.model.Proposta;
import br.com.cartao.proposta.utils.Encoder;

public class DadosCartaoProposta {

    private final String idCartao;
    private final String idProposta;
    private final String numeroCartao;

    public DadosCartaoProposta(String idCartao, String idProposta, String numeroCartao) {
        this.idCartao = idCartao;
        this.idProposta = idProposta;
        this.numeroCartao = numeroCartao;
    }

    public DadosCartaoProposta(Proposta propostaAtualizadaComCartao) {
        this.idCartao = propostaAtualizadaComCartao.getCartao().getId();
        this.idProposta = propostaAtualizadaComCartao.getId();
        this.numeroCartao = Encoder.decode(propostaAtualizadaComCartao.getCartao().getNumeroCartao());
    }

    public String getIdCartao() {
        return idCartao;
    }

    public String getIdProposta() {
        return idProposta;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

}
