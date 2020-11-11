package br.com.zup.cartaoproposta.services.cartao;

import br.com.zup.cartaoproposta.clienteswebservices.CartoesClient;
import br.com.zup.cartaoproposta.entities.cartao.bloqueio.BloqueioRequest;
import br.com.zup.cartaoproposta.entities.cartao.bloqueio.BloqueioRetornoLegado;
import br.com.zup.cartaoproposta.entities.cartao.bloqueio.ResultadoBloqueio;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

/**
 * Contagem de carga intrínseca da classe: 9
 */

@Component
public class BloquearCartao {

    @Autowired
    //1
    private CartoesClient cartoesClient;

    private final Logger logger = LoggerFactory.getLogger(BloquearCartao.class);

    public void bloquearCartaoLegado(String nCartao) {

        //1
        String nCartaoParaLog = AuxCartao.numeroCartaoOfuscado(nCartao);

        //2
        try {
            logger.info("Solicitação de bloqueio do cartão. numeroCartaoLegado: {}", nCartaoParaLog);
            //2
            BloqueioRetornoLegado bloqueio = cartoesClient.bloqueiaCartoesResource(nCartao, new BloqueioRequest("cartao-proposta"));

            //2
            if (bloqueio.getResultado() == ResultadoBloqueio.BLOQUEADO) {
                logger.info("Cartão bloqueado. numeroCartaoLegado: {}", nCartaoParaLog);
                return;
            }

            logger.warn("Tentativa invalida de bloqueio do cartão. numeroCartaoLegado: {}; resultadoRetornado: {}", nCartaoParaLog, bloqueio.getResultado());

        } catch (FeignException e) {
            /*
                Se retornou com status de FALHA,
                o cartão já foi bloqueado anteriormente
             */

            int resultadoEsperadoStatusCode = 422;
            String resultadoEsperadoTexto = "{\"resultado\":\"FALHA\"}";

            //1
            if (e.status() == resultadoEsperadoStatusCode
                    && e.contentUTF8().equals(resultadoEsperadoTexto)) {
                logger.info("Cartão já estava bloqueado. numeroCartaoLegado: {}", nCartaoParaLog);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cartão já bloqueado.");
            }
            logger.warn("Tentativa invalida de bloqueio do cartão. numeroCartaoLegado: {}; statusRetornado: {}, conteudoRetornado: {}", nCartaoParaLog, e.status(), e.contentUTF8());
        }

        logger.error("Erro no bloqueio do cartão. numeroCartaoLegado: {}", nCartaoParaLog);
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro no bloqueio do cartão. Tente novamente mais tarde.");
    }
}
