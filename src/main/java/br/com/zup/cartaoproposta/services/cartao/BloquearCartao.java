package br.com.zup.cartaoproposta.services.cartao;

import br.com.zup.cartaoproposta.clienteswebservices.CartoesClient;
import br.com.zup.cartaoproposta.entities.cartao.DadosCartaoRetornoLegado;
import br.com.zup.cartaoproposta.entities.cartao.bloqueio.BloqueioRequest;
import br.com.zup.cartaoproposta.entities.cartao.bloqueio.BloqueioRetornoLegado;
import br.com.zup.cartaoproposta.entities.cartao.bloqueio.ResultadoBloqueio;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Contagem de carga intrínseca da classe: 9
 */

@Component
public class BloquearCartao {

    @Autowired
    //1
    private CartoesClient cartoesClient;

    private final Logger logger = LoggerFactory.getLogger(BloquearCartao.class);

    //1
    public BloqueioRetornoLegado bloquearCartaoLegado(String nCartao) {

        //1
        String nCartaoParaLog = AuxCartao.numeroCartaoOfuscado(nCartao);

        DadosCartaoRetornoLegado dados;

        //2
        try {
            logger.info("Solicitação de bloqueio do cartão. numeroCartaoLegado: {}", nCartaoParaLog);
            //2
            BloqueioRetornoLegado bloqueio = cartoesClient.bloqueiaCartoesResource(nCartao, new BloqueioRequest("cartao-proposta"));

            //1
            if (bloqueio.getResultado() == ResultadoBloqueio.BLOQUEADO) {
                logger.info("Cartão bloqueado. numeroCartaoLegado: {}", nCartaoParaLog);
            }
            return bloqueio;
        } catch (FeignException e) {
            /*
                Se retornou com status de FALHA,
                verifica se o cartão já não foi bloqueado anteriormente
             */

            int resultadoEsperadoStatusCode = 422;
            String resultadoEsperadoTexto = "{\"resultado\":\"FALHA\"}";
            //1
            if (e.status() == resultadoEsperadoStatusCode
                    && e.contentUTF8().equals(resultadoEsperadoTexto)) {
                logger.info("Cartão já estava bloqueado. numeroCartaoLegado: {}", nCartaoParaLog);
                return new BloqueioRetornoLegado(ResultadoBloqueio.FALHA);
            }
        }
        return null;
    }
}
