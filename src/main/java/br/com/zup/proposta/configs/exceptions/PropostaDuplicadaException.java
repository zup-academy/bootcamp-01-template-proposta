package br.com.zup.proposta.configs.exceptions;

public class PropostaDuplicadaException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    public PropostaDuplicadaException (String msg) {
        super(msg);
    }
}
