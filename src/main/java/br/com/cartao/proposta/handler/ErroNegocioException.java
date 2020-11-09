package br.com.cartao.proposta.handler;

import org.springframework.http.HttpStatus;

/**
 * Carga intrínseca máxima permitida - 7
 * Carga intrínseca da classe - 0
 */

public class ErroNegocioException extends RuntimeException{

    private HttpStatus httpStatus;
    private String mensagem;

    public ErroNegocioException(HttpStatus httpStatus, String mensagem) {
        super(mensagem);
        this.httpStatus = httpStatus;
        this.mensagem = mensagem;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
