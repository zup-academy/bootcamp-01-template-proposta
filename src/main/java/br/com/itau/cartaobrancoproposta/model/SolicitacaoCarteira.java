package br.com.itau.cartaobrancoproposta.model;

import javax.validation.constraints.NotBlank;

public class SolicitacaoCarteira {

    @NotBlank
    private final String id;
    @NotBlank
    private final ResultadoCarteiraAssociada resultado;

    public SolicitacaoCarteira(@NotBlank String id, @NotBlank ResultadoCarteiraAssociada resultado) {
        this.id = id;
        this.resultado = resultado;
    }

    public String getId() {
        return id;
    }

    public ResultadoCarteiraAssociada getResultado() {
        return resultado;
    }
}
