package br.com.zup.proposta.exceptionHandler;

public class ErrorObject {
    private final String message;
    private final String field;
    private final Object parameter;

    public ErrorObject(String message, String campo, Object parametro) {
        this.message = message;
        this.field = campo;
        this.parameter = parametro;
    }

    public String getMessage() {
        return message;
    }

    public String getField() {
        return field;
    }

    public Object getParameter() {
        return parameter;
    }
}
