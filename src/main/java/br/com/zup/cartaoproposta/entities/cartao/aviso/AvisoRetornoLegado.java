package br.com.zup.cartaoproposta.entities.cartao.aviso;

/**
 * Contagem de carga intrínseca da classe: 1
 */

public class AvisoRetornoLegado {

    //1
    private ResultadoAviso resultado;

    @Deprecated
    public AvisoRetornoLegado(){}

    public AvisoRetornoLegado(ResultadoAviso resultado) {
        this.resultado = resultado;
    }

    public ResultadoAviso getResultado() {
        return resultado;
    }
}
