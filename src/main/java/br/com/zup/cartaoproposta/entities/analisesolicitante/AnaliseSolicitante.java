package br.com.zup.cartaoproposta.entities.analisesolicitante;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Contagem de carga intrínseca da classe: 0
 */

public class AnaliseSolicitante {
    @NotBlank
    private String documento;
    @NotNull
    private String nome;
    private String idProposta;

    public AnaliseSolicitante(@NotBlank String documento, @NotNull String nome, String idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.idProposta = idProposta;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getIdProposta() {
        return idProposta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnaliseSolicitante)) return false;
        AnaliseSolicitante that = (AnaliseSolicitante) o;
        return documento.equals(that.documento) &&
                nome.equals(that.nome) &&
                Objects.equals(idProposta, that.idProposta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documento, nome, idProposta);
    }
}
