package br.com.proposta.services;

import br.com.proposta.dtos.requests.BloqueioRequest;
import br.com.proposta.dtos.responses.BloqueioResponse;
import br.com.proposta.dtos.responses.CartaoResponse;
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


    private IntegracaoCartaoService integracaoCartaoService;

    private BloqueioRepository bloqueioRepository;


    public CartaoBloqueioService(IntegracaoCartaoService integracaoCartaoService,
                                 BloqueioRepository bloqueioRepository) {
        this.integracaoCartaoService = integracaoCartaoService;
        this.bloqueioRepository = bloqueioRepository;
    }

    private final Logger logger = LoggerFactory.getLogger(Proposta.class);


    public BloqueioResponse bloquear(String propostaId, List<String> userAgentEInternetProtocol){


        CartaoResponse cartaoResponse = integracaoCartaoService.buscarCartao(propostaId).getBody();

        BloqueioResponse bloqueioResponse = integracaoCartaoService
                .avisarLegadoBloqueioDoCartao(cartaoResponse.getId(), new BloqueioRequest("api-cartoes")).getBody();


        Bloqueio novoBloqueio =
                new Bloqueio(cartaoResponse.getId(), userAgentEInternetProtocol.get(1),
                        userAgentEInternetProtocol.get(0), StatusBloqueio.BLOQUEADO);


        bloqueioRepository.save(novoBloqueio);


        logger.info("Tentativa de bloqueio de cartão. Resultado={}", bloqueioResponse.getResultado());

        return bloqueioResponse;

    }
}
