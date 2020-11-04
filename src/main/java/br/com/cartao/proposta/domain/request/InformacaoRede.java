package br.com.cartao.proposta.domain.request;

public class InformacaoRede {

    private final String userAgent;
    private final String ipAddress;

    public InformacaoRede(String userAgent, String ipAddress) {
        this.userAgent = userAgent;
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getIpAddress() {
        return ipAddress;
    }
}
