package br.com.zup.cartaoproposta.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Contagem de carga intrínseca da classe: 0
 */

@Configuration
public class ChaveSalt {

    @Value("${solicitacao.chave-criptografar-documento}")
    private String chave;

    public String getChave() {
        return chave;
    }
}