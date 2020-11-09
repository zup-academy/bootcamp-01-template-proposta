package br.com.cartao.proposta.handler;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Carga intrínseca máxima permitida - 9
 * Carga intrínseca da classe - 0
 */

public class ErroPadraoApi {

    private LocalDateTime instante;
    private String status;
    private List<String> erros;
    private String pilhaDeErro;

    public ErroPadraoApi(String status, List<String> erros, String pilhaDeErro) {
        this.instante = LocalDateTime.now();
        this.status = status;
        this.erros = erros;
        this.pilhaDeErro = pilhaDeErro;
    }

    public LocalDateTime getInstante() {
        return instante;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getErros() {
        return erros;
    }

    public String getPilhaDeErro() {
        return pilhaDeErro;
    }

    @Override
    public String toString() {
        return "ErroPadraoApi{" +
                "instante=" + instante +
                ", status='" + status + '\'' +
                ", erros=" + erros +
                ", pilhaDeErro='" + pilhaDeErro + '\'' +
                '}';
    }
}
