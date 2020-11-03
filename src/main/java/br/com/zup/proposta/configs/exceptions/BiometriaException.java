package br.com.zup.proposta.configs.exceptions;

public class BiometriaException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    public BiometriaException(String msg) {
        super(msg);
    }
}
