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


@RestController
@RequestMapping("/bloqueios/{propostaId}")
public class BloqueioCartaoController {


    @Autowired
    private CartaoBloqueioService cartaoBloqueioService;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private EntityManager entityManager;


    private final Logger logger = LoggerFactory.getLogger(Cartao.class);


    @PostMapping
    public ResponseEntity<?> bloqueia(@PathVariable String propostaId, UriComponentsBuilder uriComponentsBuilder,
                                      @RequestHeader HttpHeaders headers, HttpServletRequest httpRequest){


        Cartao cartao = cartaoRepository.findByProposta(entityManager.find(Proposta.class, Long.parseLong(propostaId)));


        BloqueioResponse bloqueioResponse = cartaoBloqueioService.bloquear(propostaId);


        String internetProtocol = httpRequest.getRemoteAddr();

        String userAgent = headers.get(HttpHeaders.USER_AGENT).get(0);


        cartao.bloqueiaCartao(internetProtocol, userAgent, StatusBloqueio.valueOf(bloqueioResponse.getResultado()));


        logger.info("Bloqueio realizado com sucesso no cartão de proposta número={}",
                cartao.getIdProposta());

        return ResponseEntity
                .created(uriComponentsBuilder.path("/bloqueios/{propostaId}").buildAndExpand(cartao.getId()).toUri()).build();

    }
}
