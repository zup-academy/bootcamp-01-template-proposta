package br.com.cartao.proposta.domain.model;

import br.com.cartao.proposta.domain.request.InformacaoRede;
import br.com.cartao.proposta.domain.response.ResultadoAvisoViagemIntegracao;

import java.util.Optional;

public class AlteraStatusAvisoViagem {

    private Cartao cartao;
    private AvisoViagem avisoViagem;
    private Optional<ResultadoAvisoViagemIntegracao> resultadoAvisoViagemIntegracao;

    public AlteraStatusAvisoViagem(Cartao cartao, AvisoViagem avisoViagem, Optional<ResultadoAvisoViagemIntegracao> resultadoAvisoViagemIntegracao) {
        this.cartao = cartao;
        this.avisoViagem = avisoViagem;
        this.resultadoAvisoViagemIntegracao = resultadoAvisoViagemIntegracao;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public AvisoViagem getAvisoViagem() {
        return avisoViagem;
    }

    public Optional<ResultadoAvisoViagemIntegracao> getResultadoAvisoViagemIntegracao() {
        return resultadoAvisoViagemIntegracao;
    }
}
