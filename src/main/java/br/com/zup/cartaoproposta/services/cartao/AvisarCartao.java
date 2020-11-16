package br.com.zup.cartaoproposta.services.cartao;

import br.com.zup.cartaoproposta.clienteswebservices.CartoesClient;
import br.com.zup.cartaoproposta.entities.cartao.aviso.AvisoNovoRequest;
import br.com.zup.cartaoproposta.entities.cartao.aviso.AvisoRetornoLegado;
import br.com.zup.cartaoproposta.entities.cartao.aviso.ResultadoAviso;
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
public class AvisarCartao {

    @Autowired
    //1
    private CartoesClient cartoesClient;

    private final Logger logger = LoggerFactory.getLogger(AvisarCartao.class);

    //1
    public void avisarCartaoLegado(String nCartao, AvisoNovoRequest novoAviso) {

        //1
        String nCartaoParaLog = AuxCartao.numeroCartaoOfuscado(nCartao);

        //2
        try {
            logger.info("Solicitação de aviso de viagem do cartão. numeroCartaoLegado: {}", nCartaoParaLog);
            //1
            AvisoRetornoLegado aviso = cartoesClient.avisaCartoesResource(nCartao, novoAviso);

            //2
            if (aviso.getResultado() == ResultadoAviso.CRIADO) {
                logger.info("Cartão avisado de viagem. numeroCartaoLegado: {}", nCartaoParaLog);
                return;
            }

            logger.warn("Tentativa invalida de aviso de viagem do cartão. numeroCartaoLegado: {}; resultadoRetornado: {}", nCartaoParaLog, aviso.getResultado());

        } catch (FeignException e) {
            int resultadoEsperadoStatusCode = 422;
            String resultadoEsperadoTexto = "{\"resultado\":\"FALHA\"}";


            //1
            if (e.status() == resultadoEsperadoStatusCode
                    && e.contentUTF8().equals(resultadoEsperadoTexto)) {
                logger.info("Falha no aviso de viagem. O aviso já deve estar criado. numeroCartaoLegado: {}", nCartaoParaLog);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Falha no aviso da viagem. Verifique se já não foi avisado.");
            }
            logger.warn("Tentativa invalida de aviso de viagem do cartão. numeroCartaoLegado: {}; statusRetornado: {}, conteudoRetornado: {}", nCartaoParaLog, e.status(), e.contentUTF8());
        }

        logger.error("Erro no aviso de viagem do cartão. numeroCartaoLegado: {}", nCartaoParaLog);
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro no aviso se viagem. Tente novamente mais tarde.");
    }
}
