package br.com.proposta.controllers;

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


    private final Logger logger = LoggerFactory.getLogger(Proposta.class);

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
                                      @RequestHeader HttpHeaders headers, HttpServletRequest httpRequest){


        Optional<Proposta> proposta = propostaRepository.findById(propostaId);


        List<String> userAgentEInternetProtocol = userAgentEInternetProtocolService
                .recuperarUserAgentEInternetProtocolNaRequisicao(headers, httpRequest);


        cartaoBloqueioService.bloquear(propostaId, userAgentEInternetProtocol);

        logger.info("Bloqueio realizado com sucesso no cartão de proposta número={}", proposta.get().getId());

        return ResponseEntity
                .created(uriComponentsBuilder.path("/bloqueios/{propostaId}").buildAndExpand(proposta.get().getId()).toUri()).build();

    }
}
