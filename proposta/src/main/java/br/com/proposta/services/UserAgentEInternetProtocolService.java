package br.com.proposta.services;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserAgentEInternetProtocolService {


        public List<String> recuperarUserAgentEInternetProtocolNaRequisicao(HttpServletRequest httpRequest){

            String internetProtocol = httpRequest.getRemoteAddr();

            String userAgent = httpRequest.getHeader("user-agent");

            List<String> userAgentEInternetProtocol = new ArrayList<>();

            userAgentEInternetProtocol.add(internetProtocol);

            userAgentEInternetProtocol.add(userAgent);

            return userAgentEInternetProtocol;

        }
}
