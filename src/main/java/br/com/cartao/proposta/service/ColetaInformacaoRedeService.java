package br.com.cartao.proposta.service;

import br.com.cartao.proposta.domain.request.InformacaoRede;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Carga intrínseca máxima permitida - 7
 * Carga intrínseca da classe - 2
 */

@Service
public class ColetaInformacaoRedeService {

    // +1
    public InformacaoRede getInformacaoRede(HttpServletRequest httpServletRequest) {
        String userAgent = httpServletRequest.getHeader("User-Agent");
        String ipAddress = httpServletRequest.getHeader("X-FORWARDED-FOR");
        // +1
        if (StringUtils.isEmpty(ipAddress)) {
            ipAddress = httpServletRequest.getRemoteAddr();
        }

        InformacaoRede informacaoRede = new InformacaoRede(userAgent,ipAddress);
        return informacaoRede;
    }
}
