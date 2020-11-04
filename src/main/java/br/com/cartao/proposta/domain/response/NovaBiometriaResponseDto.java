package br.com.cartao.proposta.domain.response;

import br.com.cartao.proposta.domain.model.Biometria;

import java.util.List;

public class NovaBiometriaResponseDto {

    private final String id;
    private final List<String> fingerprint;
    private final String idCartao;

    public NovaBiometriaResponseDto(Biometria biometria) {
        this.id = biometria.getId();
        this.fingerprint = biometria.getFingerprint();
        this.idCartao = biometria.getIdCartao();
    }

    public String getId() {
        return id;
    }

    public List<String> getFingerprint() {
        return fingerprint;
    }

    public String getIdCartao() {
        return idCartao;
    }

    @Override
    public String toString() {
        return "NovaBiometriaResponseDto{" +
                "id='" + id + '\'' +
                ", fingerprint=" + fingerprint +
                ", idCartao='" + idCartao + '\'' +
                '}';
    }
}
