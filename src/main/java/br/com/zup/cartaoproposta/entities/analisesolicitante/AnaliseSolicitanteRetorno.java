package br.com.zup.cartaoproposta.entities.analisesolicitante;

import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Contagem de carga intrínseca da classe: 1
 */

public class AnaliseSolicitanteRetorno {

    @NotBlank
    private String documento;
    @NotNull
    private String nome;
    @Enumerated
    //1
    private ResultadoSolicitacao resultadoSolicitacao;
    private String idProposta;

    public AnaliseSolicitanteRetorno(@NotBlank String documento, @NotNull String nome, ResultadoSolicitacao resultadoSolicitacao, String idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.resultadoSolicitacao = resultadoSolicitacao;
        this.idProposta = idProposta;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public ResultadoSolicitacao getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }

    public String getIdProposta() {
        return idProposta;
    }
}
