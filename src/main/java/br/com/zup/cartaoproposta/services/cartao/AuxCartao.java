package br.com.zup.cartaoproposta.services.cartao;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Contagem de carga intrínseca da classe: 2
 */

@Component
public class AuxCartao {

    public static String getIpAdress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        //1
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        //1
        if (ipAddress.equals("0:0:0:0:0:0:0:1")) {
            ipAddress = "127.0.0.1";
        }
        return ipAddress;
    }

    public static String numeroCartaoOfuscado(String nCartao) {
        int tamanho = nCartao.length();
        String ofuscado = "*".repeat(tamanho - 5);
        ofuscado += nCartao.substring(tamanho-5,tamanho);
        return ofuscado;
    }
}
