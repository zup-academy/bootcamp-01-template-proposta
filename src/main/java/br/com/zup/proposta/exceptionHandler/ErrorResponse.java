package br.com.zup.proposta.exceptionHandler;

import java.util.List;

public class ErrorResponse {

    private final String message;
    private final int code;
    private final String status;
    private final List<ErrorObject> errors; //1

    public ErrorResponse(String message, int code, String status, List<ErrorObject> errors) {
        this.message = message;
        this.code = code;
        this.status = status;
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public List<ErrorObject> getErrors() {
        return errors;
    }
}
