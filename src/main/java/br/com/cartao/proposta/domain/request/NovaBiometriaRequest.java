package br.com.cartao.proposta.domain.request;

import br.com.cartao.proposta.domain.model.Biometria;
import br.com.cartao.proposta.domain.model.FingerPrint;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Carga intrínseca máxima permitida - 9
 * Carga intrínseca da classe - 5
 */

public class NovaBiometriaRequest {

    @NotNull
    @Size(min = 1)
    // +1
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
    // +1
    public Biometria toModel(String idCartao){
        // +1
        List<FingerPrint> printFinger = this.biometrias.stream()
                // +1
                .map(printfingerRequest -> {
                    return printfingerRequest.toModel();
                })
                // +1
                .collect(Collectors.toList());

        return new Biometria(printFinger, idCartao);
    }

}
