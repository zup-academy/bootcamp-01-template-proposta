package io.github.evertocnsouza.domain.enums;

import java.util.stream.Stream;

public enum TipoCarteira {
    PAYPAL,
    SAMSUNG_PAY;

    public static boolean validarCarteira(String carteira){
        return Stream.of(TipoCarteira.class.getFields()).anyMatch(field -> field.getName().equals(carteira));
    }
}