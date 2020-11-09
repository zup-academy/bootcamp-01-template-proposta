package br.com.cartao.proposta.service;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

public class EncodeValor {

    private static TextEncryptor salt = Encryptors.text( "secretKey", "5c0744940b5c369b");

    public static String encode(String valor){
        return salt.encrypt(valor);
    }

    public static String decode(String chave){
        return salt.decrypt(chave);
    }

    public static String encondeBcrypt(String valor){
        return BCrypt.hashpw(valor, BCrypt.gensalt());
    }
}
