package br.com.itau.cartaobrancoproposta.model;

public class AvisoViagem {

    private String validoAte;
    private String destino;

    public AvisoViagem(String validoAte, String destino) {
        this.validoAte = validoAte;
        this.destino = destino;
    }

    public String getValidoAte() {
        return validoAte;
    }

    public void setValidoAte(String validoAte) {
        this.validoAte = validoAte;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }
}
