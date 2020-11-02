package br.com.proposta.compartilhado;

import br.com.proposta.entidades.Proposta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class BuscarIPeUserAgentNaRequisicao {


       private final Logger logger = LoggerFactory.getLogger(HttpServletRequest.class);

        public List<String> recuperarUserAgentEInternetProtocolNaRequisicao(HttpServletRequest httpRequest){


            var internetProtocol = httpRequest.getRemoteAddr();

            var userAgent = httpRequest.getHeader("user-agent");

            /* @complexidade - lista criada apenas nesse contexto */
            var userAgentEInternetProtocol = new ArrayList<String>();

            userAgentEInternetProtocol.add(internetProtocol);

            userAgentEInternetProtocol.add(userAgent);


            logger.info("Informações de origem da requisição foram buscadas com sucesso");


            return userAgentEInternetProtocol;

        }
}
