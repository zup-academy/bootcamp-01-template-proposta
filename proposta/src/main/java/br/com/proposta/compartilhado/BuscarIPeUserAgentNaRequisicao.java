package br.com.proposta.compartilhado;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class BuscarIPeUserAgentNaRequisicao {



        public List<String> recuperarUserAgentEInternetProtocolNaRequisicao(HttpServletRequest httpRequest){


            var internetProtocol = httpRequest.getRemoteAddr();

            var userAgent = httpRequest.getHeader("user-agent");


            var userAgentEInternetProtocol = new ArrayList<String>();

            userAgentEInternetProtocol.add(internetProtocol);

            userAgentEInternetProtocol.add(userAgent);


            return userAgentEInternetProtocol;

        }
}
