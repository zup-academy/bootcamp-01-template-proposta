package br.com.cartao.proposta.domain.request;

import br.com.cartao.proposta.domain.model.Biometria;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;

public class NovaBiometriaRequest {

    @NotNull
    @Size(min = 1)
    private List<String> fingerprint;

    @Deprecated
    public NovaBiometriaRequest() {
    }

    public NovaBiometriaRequest(List<String> fingerprint) {
        this.fingerprint = fingerprint;
    }

    public List<String> getFingerprint() {
        return fingerprint;
    }

    @Override
    public String toString() {
        return "NovaBiometriaRequest{" +
                "fingerprint=" + fingerprint +
                '}';
    }

    public Biometria toModel(String idCartao){
        return new Biometria(this.fingerprint, idCartao);
    }

}
