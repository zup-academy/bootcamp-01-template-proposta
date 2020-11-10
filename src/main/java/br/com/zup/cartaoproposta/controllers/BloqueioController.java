package br.com.zup.cartaoproposta.controllers;

import br.com.zup.cartaoproposta.entities.cartao.Cartao;
import br.com.zup.cartaoproposta.entities.cartao.bloqueio.BloqueioCartao;
import br.com.zup.cartaoproposta.entities.cartao.bloqueio.BloqueioRetornoLegado;
import br.com.zup.cartaoproposta.entities.cartao.bloqueio.ResultadoBloqueio;
import br.com.zup.cartaoproposta.repositories.CartaoRepository;
import br.com.zup.cartaoproposta.services.cartao.AuxCartao;
import br.com.zup.cartaoproposta.services.cartao.BloquearCartao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.net.URI;
import java.util.Optional;

/**
 * Contagem de carga intrínseca da classe: 9
 */

@RestController
@RequestMapping("/cartoes/bloqueio")
public class BloqueioController {

    @Autowired
    //1
    CartaoRepository cartaoRepository;

    @Autowired
    //1
    BloquearCartao bloquearCartao;

    @PersistenceContext
    EntityManager manager;

    private final Logger logger = LoggerFactory.getLogger(BloqueioController.class);

    @PostMapping("/{id}")
    @Transactional
    public ResponseEntity<String> cadastroBloqueio(@PathVariable("id") String nCartao, UriComponentsBuilder uriComponentsBuilder, @AuthenticationPrincipal Jwt principal, HttpServletRequest request){

        //1
        String nCartaoParaLog = AuxCartao.numeroCartaoOfuscado(nCartao);

        //1
        Optional<Cartao> cartaoR = cartaoRepository.findByIdLegado(nCartao);

        //1
        if(cartaoR.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Cartao cartao = cartaoR.get();

        //1
        BloqueioRetornoLegado cartaoBloqueado = bloquearCartao.bloquearCartaoLegado(nCartao);

        //1
        if (cartaoBloqueado == null) {
            logger.error("Erro no bloqueio do cartão. numeroCartaoLegado: {}", nCartaoParaLog);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro no bloqueio do cartão. Tente novamente mais tarde.");
        }
        //1
        if (cartaoBloqueado.getResultado() == ResultadoBloqueio.FALHA) {
            logger.warn("Tentativa de bloqueio em cartão já bloqueado. numeroCartaoLegado: {}", nCartaoParaLog);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cartão já bloqueado.");
        }

        String idUser = principal.getClaimAsString("sub");
        String ipAddress = AuxCartao.getIpAdress(request);

        //1
        BloqueioCartao bloqueioCartao = new BloqueioCartao(true, ipAddress, idUser, cartao);

        manager.persist(bloqueioCartao);

        URI link = uriComponentsBuilder.path("/cartoes/bloqueio/{id}").buildAndExpand(bloqueioCartao.getId()).toUri();
        return ResponseEntity.created(link).build();

    }
}
