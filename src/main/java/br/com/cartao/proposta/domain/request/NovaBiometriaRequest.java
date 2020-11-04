package br.com.cartao.proposta.domain.request;

import br.com.cartao.proposta.domain.model.Biometria;
import br.com.cartao.proposta.domain.model.FingerPrint;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

public class NovaBiometriaRequest {

    @NotNull
    @Size(min = 1)
    private List<PrintfingerRequest> biometrias;

    @Deprecated
    public NovaBiometriaRequest() {
    }

    public NovaBiometriaRequest(List<PrintfingerRequest> biometrias) {
        this.biometrias = biometrias;
    }

    public List<PrintfingerRequest> getBiometrias() {
        return biometrias;
    }

    @Override
    public String toString() {
        return "NovaBiometriaRequest{" +
                "fingerprint=" + biometrias +
                '}';
    }

    public Biometria toModel(String idCartao){

        List<FingerPrint> printFinger = this.biometrias.stream()
                .map(printfingerRequest -> {
                    return printfingerRequest.toModel();
                })
                // +1
                .collect(Collectors.toList());

        return new Biometria(printFinger, idCartao);
    }

}
