package br.com.zup.cartaoproposta.services.cartao;

import br.com.zup.cartaoproposta.clienteswebservices.CartoesClient;
import br.com.zup.cartaoproposta.entities.cartao.DadosCartaoRetornoLegado;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Contagem de carga intrínseca da classe: 5
 */

@Component
public class DadosCartao {

    @Autowired
    //1
    private CartoesClient cartoesClient;

    private final Logger logger = LoggerFactory.getLogger(DadosCartao.class);

    //1
    public DadosCartaoRetornoLegado dadosDoCartaoLegado(String nCartao) {

        //1
        String nCartaoParaLog = AuxCartao.numeroCartaoOfuscado(nCartao);

        DadosCartaoRetornoLegado dadosCartao = null;

        //2
        try {
            logger.info("Busca dos dados do cartão. numeroCartaoLegado: {}", nCartaoParaLog);
            dadosCartao = cartoesClient.dadosCartaoPeloId(nCartao);
        } catch (FeignException e) {
            logger.warn("Não localizado os dados do cartão. numeroCartaoLegado: {}; statusRetorno: {}",nCartaoParaLog, e.status());
        }

         return dadosCartao;
    }
}
