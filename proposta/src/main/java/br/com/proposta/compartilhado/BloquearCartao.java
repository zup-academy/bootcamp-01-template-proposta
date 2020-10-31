package br.com.proposta.compartilhado;

import br.com.proposta.dtos.requests.BloqueioRequest;
import br.com.proposta.dtos.responses.BloqueioResponse;
import br.com.proposta.entidades.Enums.StatusBloqueio;
import br.com.proposta.integracoes.IntegracaoApiCartoes;
import br.com.proposta.entidades.Bloqueio;
import br.com.proposta.entidades.Proposta;
import br.com.proposta.repositories.BloqueioRepository;
import br.com.proposta.repositories.CartaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BloquearCartao {

    /* total de pontos = 7 */

    /* @complexidade - acoplamento contextual */
    private final IntegracaoApiCartoes integracaoApiCartoes;

    /* @complexidade - acoplamento contextual */
    private BloqueioRepository bloqueioRepository;

    @Autowired
    private CartaoRepository cartaoRepository;


    public BloquearCartao(IntegracaoApiCartoes integracaoApiCartoes,
                          BloqueioRepository bloqueioRepository) {
        this.integracaoApiCartoes = integracaoApiCartoes;
        this.bloqueioRepository = bloqueioRepository;
    }

    private final Logger logger = LoggerFactory.getLogger(Proposta.class);

    public BloqueioResponse bloquear(String cartaoId, List<String> userAgentEInternetProtocol){


        /* @complexidade - classe criada no projeto + @complexidade - classe criada no projeto */
        var cartao = cartaoRepository.findByNumero(cartaoId);


        /* @complexidade - classe criada no projeto + @complexidade - classe criada no projeto */
        var bloqueioResponse = integracaoApiCartoes
                .avisarLegadoBloqueioDoCartao(cartao.getNumero(), new BloqueioRequest("api-cartoes"))
                .getBody();


        /* @complexidade - função como parâmetro */
        cartao.setStatus(StatusBloqueio.valueOf(bloqueioResponse.getResultado()));

        /* @complexidade - classe criada no projeto */
        var novoBloqueio = new Bloqueio(userAgentEInternetProtocol, bloqueioResponse);

        bloqueioRepository.save(novoBloqueio);


        logger.info("Tentativa de bloqueio de cartão. Resultado={}", bloqueioResponse.getResultado());

        return bloqueioResponse;

    }
}
