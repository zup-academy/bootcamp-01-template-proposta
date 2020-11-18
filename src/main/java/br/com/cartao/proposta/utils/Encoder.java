package br.com.cartao.proposta.utils;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

public class Encoder {

    private static TextEncryptor salt = Encryptors.text( "secretKey", "5c0744940b5c369b");

    public static String encode(String valor){
        return salt.encrypt(valor);
    }

    public static String decode(String chave){
        return salt.decrypt(chave);
    }

}
