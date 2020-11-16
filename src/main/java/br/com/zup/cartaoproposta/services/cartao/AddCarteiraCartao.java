package br.com.zup.cartaoproposta.services.cartao;

import br.com.zup.cartaoproposta.clienteswebservices.CartoesClient;
import br.com.zup.cartaoproposta.entities.cartao.carteira.CarteiraNovoRequest;
import br.com.zup.cartaoproposta.entities.cartao.carteira.CarteiraRetornoLegado;
import br.com.zup.cartaoproposta.entities.cartao.carteira.ResultadoCarteira;
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
public class AddCarteiraCartao {

    @Autowired
    //1
    private CartoesClient cartoesClient;

    private final Logger logger = LoggerFactory.getLogger(AddCarteiraCartao.class);

    //1
    public void addCarteiraCartaoLegado(String nCartao, CarteiraNovoRequest novaCarteira) {

        //1
        String nCartaoParaLog = AuxCartao.numeroCartaoOfuscado(nCartao);

        //2
        try {
            logger.info("Solicitação de adicionar carteira no cartão. numeroCartaoLegado: {}", nCartaoParaLog);
            //1
            CarteiraRetornoLegado carteira = cartoesClient.carteiraCartoesResource(nCartao, novaCarteira);

            //2
            if (carteira.getResultado() == ResultadoCarteira.ASSOCIADA) {
                logger.info("Carteira adicionada. numeroCartaoLegado: {}", nCartaoParaLog);
                return;
            }

            logger.warn("Tentativa invalida de bloqueio do cartão. numeroCartaoLegado: {}; resultadoRetornado: {}", nCartaoParaLog, carteira.getResultado());

        } catch (FeignException e) {

            int resultadoEsperadoStatusCode = 422;
            String resultadoEsperadoTexto = "{\"resultado\":\"FALHA\",\"id\":null}";

            //1
            if (e.status() == resultadoEsperadoStatusCode
                    && e.contentUTF8().equals(resultadoEsperadoTexto)) {
                logger.info("Falha ao adicionar a carteira. A carteira pode já estar adicionada. numeroCartaoLegado: {}", nCartaoParaLog);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Falha na adição da carteira. Verifique se já não foi avisado.");
            }
            logger.warn("Tentativa invalida de adicionar carteira. numeroCartaoLegado: {}; statusRetornado: {}, conteudoRetornado: {}", nCartaoParaLog, e.status(), e.contentUTF8());
        }

        logger.error("Erro ao adicionar carteira no cartão. numeroCartaoLegado: {}", nCartaoParaLog);
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao adicionar a carteira. Tente novamente mais tarde.");
    }
}
