package br.com.zup.cartaoproposta.entities.cartao.bloqueio;

/**
 * Contagem de carga intrínseca da classe: 1
 */

public class BloqueioRetornoLegado {
    //1
    private ResultadoBloqueio resultado;

    @Deprecated
    public BloqueioRetornoLegado() {
    }

    public BloqueioRetornoLegado(ResultadoBloqueio resultado) {
        this.resultado = resultado;
    }

    public ResultadoBloqueio getResultado() {
        return resultado;
    }
}
