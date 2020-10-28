package br.com.proposta.controllers;

import br.com.proposta.dtos.requests.BloqueioRequest;
import br.com.proposta.dtos.responses.BloqueioResponse;
import br.com.proposta.dtos.responses.CartaoResponse;
import br.com.proposta.dtos.responses.ResultadoAnaliseResponse;
import br.com.proposta.models.Cartao;
import br.com.proposta.models.Enums.StatusBloqueio;
import br.com.proposta.models.Proposta;
import br.com.proposta.repositories.PropostaRepository;
import br.com.proposta.services.CartaoBloqueioService;
import br.com.proposta.services.IntegracaoCartaoService;
import br.com.proposta.repositories.CartaoRepository;
import br.com.proposta.services.UserAgentEInternetProtocolService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/bloqueios/{propostaId}")
public class BloqueioCartaoController {


    private final Logger logger = LoggerFactory.getLogger(Cartao.class);

    private CartaoBloqueioService cartaoBloqueioService;

    private UserAgentEInternetProtocolService userAgentEInternetProtocolService;

    private CartaoRepository cartaoRepository;

    private EntityManager entityManager;



    public BloqueioCartaoController(CartaoBloqueioService cartaoBloqueioService, UserAgentEInternetProtocolService userAgentEInternetProtocolService,
                                    CartaoRepository cartaoRepository, EntityManager entityManager) {
        this.cartaoBloqueioService = cartaoBloqueioService;
        this.userAgentEInternetProtocolService = userAgentEInternetProtocolService;
        this.cartaoRepository = cartaoRepository;
        this.entityManager = entityManager;
    }



    @PostMapping
    public ResponseEntity<?> bloqueia(@PathVariable String propostaId, UriComponentsBuilder uriComponentsBuilder,
                                      @RequestHeader HttpHeaders headers, HttpServletRequest httpRequest){


        Cartao cartao = cartaoRepository.findByProposta(entityManager.find(Proposta.class, Long.parseLong(propostaId)));

        List<String> userAgentEInternetProtocol = userAgentEInternetProtocolService
                .recuperarUserAgentEInternetProtocolNaRequisicao(headers, httpRequest);

        cartao.bloqueiaCartao(userAgentEInternetProtocol.get(0), userAgentEInternetProtocol.get(1));


        cartaoBloqueioService.bloquear(propostaId);


        logger.info("Bloqueio realizado com sucesso no cartão de proposta número={}",
                cartao.getIdProposta());

        return ResponseEntity
                .created(uriComponentsBuilder.path("/bloqueios/{propostaId}").buildAndExpand(cartao.getId()).toUri()).build();

    }
}
