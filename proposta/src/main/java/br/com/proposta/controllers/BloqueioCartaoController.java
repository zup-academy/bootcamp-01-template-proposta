package br.com.proposta.controllers;

import br.com.proposta.models.Bloqueio;
import br.com.proposta.repositories.PropostaRepository;
import br.com.proposta.services.CartaoBloqueioService;
import br.com.proposta.services.UserAgentEInternetProtocolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;
import java.util.List;



@RestController
@RequestMapping("/api/bloqueios/{propostaId}")
public class BloqueioCartaoController {

    /* total de pontos = 6 */

    private final Logger logger = LoggerFactory.getLogger(Bloqueio.class);

    /* @complexidade - classe criada no projeto */
    private final CartaoBloqueioService cartaoBloqueioService;

    /* @complexidade - classe criada no projeto */
    private final UserAgentEInternetProtocolService userAgentEInternetProtocolService;

    /* @complexidade - classe criada no projeto */
    private final PropostaRepository propostaRepository;


    public BloqueioCartaoController(CartaoBloqueioService cartaoBloqueioService, UserAgentEInternetProtocolService userAgentEInternetProtocolService,
                                    PropostaRepository propostaRepository) {
        this.cartaoBloqueioService = cartaoBloqueioService;
        this.userAgentEInternetProtocolService = userAgentEInternetProtocolService;
        this.propostaRepository = propostaRepository;
    }



    @PostMapping
    public ResponseEntity<?> bloqueia(@PathVariable String propostaId, UriComponentsBuilder uriComponentsBuilder,
                                    HttpServletRequest httpRequest){


        /* @complexidade - classe criada no projeto */
        var userAgentEInternetProtocol = userAgentEInternetProtocolService
                .recuperarUserAgentEInternetProtocolNaRequisicao(httpRequest);

        /* @complexidade - classe criada no projeto */
        var bloqueioResponse = cartaoBloqueioService.bloquear(propostaId, userAgentEInternetProtocol);

        /* @complexidade - classe criada no projeto */
        var bloqueio = new Bloqueio(userAgentEInternetProtocol, bloqueioResponse);

        logger.info("Bloqueio realizado com sucesso no cartão");

        return ResponseEntity
                .created(uriComponentsBuilder.path("/bloqueios/{propostaId}").buildAndExpand(bloqueio.getId()).toUri()).build();

    }
}
