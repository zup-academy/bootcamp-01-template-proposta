package br.com.proposta.compartilhado;

import br.com.proposta.transferenciadados.requisicoes.RequisicaoBloqueio;
import br.com.proposta.transferenciadados.respostas.RespostaBloqueio;
import br.com.proposta.integracoes.IntegracaoApiCartoes;
import br.com.proposta.entidades.Bloqueio;
import br.com.proposta.entidades.Enums.StatusBloqueio;
import br.com.proposta.entidades.Proposta;
import br.com.proposta.repositorios.BloqueioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BloquearCartao {

    /* total de pontos = 7 */

    /* @complexidade - classe criada no projeto */
    private final IntegracaoApiCartoes integracaoApiCartoes;

    /* @complexidade - classe criada no projeto */
    private BloqueioRepository bloqueioRepository;


    public BloquearCartao(IntegracaoApiCartoes integracaoApiCartoes,
                          BloqueioRepository bloqueioRepository) {
        this.integracaoApiCartoes = integracaoApiCartoes;
        this.bloqueioRepository = bloqueioRepository;
    }

    private final Logger logger = LoggerFactory.getLogger(Proposta.class);


    public RespostaBloqueio bloquear(String propostaId, List<String> userAgentEInternetProtocol){


        /* @complexidade - classe criada no projeto + @complexidade - classe criada no projeto */
        var cartaoResponse = integracaoApiCartoes.buscarCartao(propostaId).getBody();


        /* @complexidade - classe criada no projeto + @complexidade - classe criada no projeto */
        var bloqueioResponse = integracaoApiCartoes
                .avisarLegadoBloqueioDoCartao(cartaoResponse.getId(), new RequisicaoBloqueio("api-cartoes")).getBody();


        /* @complexidade - classe criada no projeto */
        var novoBloqueio = new Bloqueio(userAgentEInternetProtocol.get(0), userAgentEInternetProtocol.get(1),
                        StatusBloqueio.valueOf(bloqueioResponse.getResultado()));


        bloqueioRepository.save(novoBloqueio);

        logger.info("Tentativa de bloqueio de cartão. Resultado={}", bloqueioResponse.getResultado());

        return bloqueioResponse;

    }
}
