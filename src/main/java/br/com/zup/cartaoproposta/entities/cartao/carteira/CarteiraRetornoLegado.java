package br.com.zup.cartaoproposta.entities.cartao.carteira;

/**
 * Contagem de carga intrínseca da classe: 1
 */

public class CarteiraRetornoLegado {

    private String id;
    //1
    private ResultadoCarteira resultado;

    @Deprecated
    public CarteiraRetornoLegado(){}

    public CarteiraRetornoLegado(String id, ResultadoCarteira resultado) {
        this.id = id;
        this.resultado = resultado;
    }

    public String getId() {
        return id;
    }

    public ResultadoCarteira getResultado() {
        return resultado;
    }
}
