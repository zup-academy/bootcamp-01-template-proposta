package br.com.zup.proposta.controller;

import br.com.zup.proposta.model.Bloqueio;
import br.com.zup.proposta.model.Cartao;
import br.com.zup.proposta.service.SolicitarBloqueio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/cartoes")
public class BloqueioCartaoController {

    private Logger logger = LoggerFactory.getLogger(BloqueioCartaoController.class);

    private EntityManager entityManager;

    @Autowired
    private SolicitarBloqueio solicitarBloqueio; //1

    public BloqueioCartaoController(SolicitarBloqueio solicitarBloqueio, EntityManager entityManager) {
        this.solicitarBloqueio = solicitarBloqueio;
        this.entityManager = entityManager;
    }

    @PostMapping("/{id}/bloqueios")
    @Transactional
    public ResponseEntity novoBloqueio(@PathVariable(value = "id") UUID idCartao,
                                       HttpServletRequest request,
                                       UriComponentsBuilder builder,
                                       @AuthenticationPrincipal Jwt jwt){

        Optional<String> emailLogado = Optional.ofNullable(jwt.getClaim("email"));

        Assert.isTrue(emailLogado.isPresent(), "Para bloquear um cartão, " +
                "deve-se estar logado com um email autorizado");

        Optional<Cartao> possivelCartao = Optional
                .ofNullable(entityManager.find(Cartao.class, idCartao)); //2

        if (possivelCartao.isEmpty()){ //2
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        logger.info("Informações do cartao: {}", possivelCartao.get().toString());

        String emailDonoCartao = possivelCartao.get().emailDonocartao(); //3

        logger.info("Email do dono do cartão: {}", emailDonoCartao);

        if (!emailDonoCartao.equals(emailLogado.get())) //4
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Você não esta autorizado a bloquear esse cartão");

        //é obrigatório fornecer?
        String userAgent = request.getHeader("user-agent");
        String ipCliente = request.getRemoteAddr();

        Bloqueio novoBloqueio = new Bloqueio(possivelCartao.get(), userAgent, ipCliente); //5

        logger.info("Capturando infomações do header da requisição: {}", novoBloqueio.toString());

        Map<String, String>  resultado = solicitarBloqueio.bloquear(novoBloqueio.numeroCartao());

        logger.info("Cartao bloqueado? {}", resultado);

        possivelCartao.get().bloquearCartao();

        logger.info("Infos do cartão: {}", possivelCartao.get().toString());

        entityManager.persist(novoBloqueio);

        return ResponseEntity.created(builder.path("/api/cartoes/{id}/bloqueios/{id}")
                .buildAndExpand(possivelCartao.get().getId(), novoBloqueio.getId())
                .toUri()).build();
    }

}
