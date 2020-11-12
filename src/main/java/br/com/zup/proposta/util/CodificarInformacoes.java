package br.com.zup.proposta.util;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;

public class CodificarInformacoes {

    private TextEncryptor codificador;

    public TextEncryptor obterCodificador(String salt){
        return Encryptors.text("password", salt);
    }

    public String codifica(String informacao, String salt){
        this.codificador = obterCodificador(salt);
        return this.codificador.encrypt(informacao);
    }

    public String decodificarInformacao(String informacaoCodificada){
        return this.codificador.decrypt(informacaoCodificada);
    }


}
