package io.github.evertocnsouza.validation;

import java.util.List;

public class ApiErrors {


    private List<String> errors;

    public List<String> getErrors() { //Método get, para gerar um get errors, para obtermos os erros.
        return errors;
    }

    public ApiErrors(List<String> errors) {
        this.errors = errors;

    }
}

