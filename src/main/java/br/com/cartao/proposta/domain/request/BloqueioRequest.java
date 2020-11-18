package br.com.cartao.proposta.domain.request;

import br.com.cartao.proposta.domain.model.Bloqueio;
import br.com.cartao.proposta.domain.model.Cartao;

public class BloqueioRequest {

    private final String ipCliente;
    private final String userAgent;

    public BloqueioRequest(String ipCliente, String userAgent) {
        this.ipCliente = ipCliente;
        this.userAgent = userAgent;
    }

    public String getIpCliente() {
        return ipCliente;
    }

    public String getUserAgent() {
        return userAgent;
    }

    @Override
    public String toString() {
        return "BloqueioRequest{" +
                "ipCliente='" + ipCliente + '\'' +
                ", userAgent='" + userAgent + '\'' +
                '}';
    }

    public Bloqueio toModel(Cartao cartao, InformacaoRede informacaoRede ) {
        return new Bloqueio(informacaoRede.getIpAddress(), informacaoRede.getUserAgent(), cartao);
    }
}
