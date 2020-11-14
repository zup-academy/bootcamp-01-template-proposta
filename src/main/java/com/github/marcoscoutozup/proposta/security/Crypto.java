package com.github.marcoscoutozup.proposta.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class Crypto {

    private static String tokenPrimario;
    private static String tokenSecundario;

    public Crypto(@Value("${token.primario}") String secretPrimario, @Value("${token.secundario}") String secretSecundario) {
        this.tokenPrimario = secretPrimario;
        this.tokenSecundario = secretSecundario;
    }

    public static String encrypt(String toEncrypt) {
        Assert.notNull(toEncrypt, "A informação para criptografia não pode ser nula");
        StringBuilder builder = new StringBuilder();
        String firstHash = DigestUtils.sha384Hex(builder.append(toEncrypt).append(tokenPrimario).toString());
        String secondHash = DigestUtils.sha512Hex(builder.append(firstHash).append(tokenSecundario).toString());
        String finalHash = DigestUtils.sha256Hex(builder.append(secondHash).append(toEncrypt.charAt(0)).append(firstHash).toString());;
        return finalHash;
    }
}
