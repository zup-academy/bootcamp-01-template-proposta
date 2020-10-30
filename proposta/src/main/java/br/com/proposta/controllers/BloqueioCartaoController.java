package br.com.proposta.controllers;

import br.com.proposta.dtos.responses.BloqueioResponse;
import br.com.proposta.models.Bloqueio;
import br.com.proposta.models.Enums.StatusBloqueio;
import br.com.proposta.models.Proposta;
import br.com.proposta.repositories.PropostaRepository;
import br.com.proposta.services.CartaoBloqueioService;
import br.com.proposta.services.UserAgentEInternetProtocolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/bloqueios/{propostaId}")
public class BloqueioCartaoController {


    private final Logger logger = LoggerFactory.getLogger(Bloqueio.class);

    private CartaoBloqueioService cartaoBloqueioService;

    private UserAgentEInternetProtocolService userAgentEInternetProtocolService;

    private PropostaRepository propostaRepository;


    public BloqueioCartaoController(CartaoBloqueioService cartaoBloqueioService, UserAgentEInternetProtocolService userAgentEInternetProtocolService,
                                    PropostaRepository propostaRepository) {
        this.cartaoBloqueioService = cartaoBloqueioService;
        this.userAgentEInternetProtocolService = userAgentEInternetProtocolService;
        this.propostaRepository = propostaRepository;
    }


    @PostMapping
    public ResponseEntity<?> bloqueia(@PathVariable String propostaId, UriComponentsBuilder uriComponentsBuilder,
                                    HttpServletRequest httpRequest){


        List<String> userAgentEInternetProtocol = userAgentEInternetProtocolService
                .recuperarUserAgentEInternetProtocolNaRequisicao(httpRequest);

        BloqueioResponse bloqueioResponse =
                cartaoBloqueioService.bloquear(propostaId, userAgentEInternetProtocol);

        Bloqueio bloqueio =
                new Bloqueio(userAgentEInternetProtocol.get(0), userAgentEInternetProtocol.get(1), StatusBloqueio.valueOf(bloqueioResponse.getResultado()));

        logger.info("Bloqueio realizado com sucesso no cartão");

        return ResponseEntity
                .created(uriComponentsBuilder.path("/bloqueios/{propostaId}").buildAndExpand(bloqueio.getId()).toUri()).build();

    }
}
