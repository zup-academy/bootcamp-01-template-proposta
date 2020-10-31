package br.com.proposta.services;

import br.com.proposta.dtos.requests.BloqueioRequest;
import br.com.proposta.dtos.responses.BloqueioResponse;
import br.com.proposta.dtos.responses.CartaoResponse;
import br.com.proposta.integracoes.IntegracaoApiCartoes;
import br.com.proposta.models.Bloqueio;
import br.com.proposta.models.Enums.StatusBloqueio;
import br.com.proposta.models.Proposta;
import br.com.proposta.repositories.BloqueioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CartaoBloqueioService {

    /* total de pontos = 7 */

    /* @complexidade - classe criada no projeto */
    private final IntegracaoApiCartoes integracaoApiCartoes;

    /* @complexidade - classe criada no projeto */
    private BloqueioRepository bloqueioRepository;


    public CartaoBloqueioService(IntegracaoApiCartoes integracaoApiCartoes,
                                 BloqueioRepository bloqueioRepository) {
        this.integracaoApiCartoes = integracaoApiCartoes;
        this.bloqueioRepository = bloqueioRepository;
    }

    private final Logger logger = LoggerFactory.getLogger(Proposta.class);


    public BloqueioResponse bloquear(String propostaId, List<String> userAgentEInternetProtocol){


        /* @complexidade - classe criada no projeto + @complexidade - classe criada no projeto */
        var cartaoResponse = integracaoApiCartoes.buscarCartao(propostaId).getBody();


        /* @complexidade - classe criada no projeto + @complexidade - classe criada no projeto */
        var bloqueioResponse = integracaoApiCartoes
                .avisarLegadoBloqueioDoCartao(cartaoResponse.getId(), new BloqueioRequest("api-cartoes")).getBody();


        /* @complexidade - classe criada no projeto */
        var novoBloqueio = new Bloqueio(userAgentEInternetProtocol.get(0), userAgentEInternetProtocol.get(1),
                        StatusBloqueio.valueOf(bloqueioResponse.getResultado()));


        bloqueioRepository.save(novoBloqueio);

        logger.info("Tentativa de bloqueio de cartão. Resultado={}", bloqueioResponse.getResultado());

        return bloqueioResponse;

    }
}
