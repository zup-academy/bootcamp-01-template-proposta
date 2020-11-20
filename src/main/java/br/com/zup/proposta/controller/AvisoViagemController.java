package br.com.zup.proposta.controller;

import br.com.zup.proposta.dto.NovoAvisoViagemRequest;
import br.com.zup.proposta.model.AvisoViagem;
import br.com.zup.proposta.model.Cartao;
import br.com.zup.proposta.service.AvisarViagem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/cartoes")
public class AvisoViagemController {

    private EntityManager entityManager;
    private AvisarViagem avisarViagem;
    private Logger logger = LoggerFactory.getLogger(AvisoViagemController.class);

    public AvisoViagemController(EntityManager entityManager, AvisarViagem avisarViagem) {
        this.entityManager = entityManager;
        this.avisarViagem = avisarViagem;
    }

    @PostMapping("/{id}/aviso-viagem")
    @Transactional
    public ResponseEntity novoAvisoViagem(@PathVariable("id")UUID idCartao,
                                          @RequestBody @Valid NovoAvisoViagemRequest request,
                                          HttpServletRequest httpRequest,
                                          UriComponentsBuilder builder,
                                          @AuthenticationPrincipal Jwt jwt){ //1

        Optional<String> emailLogado = Optional.ofNullable(jwt.getClaim("email"));
        Assert.isTrue(emailLogado.isPresent(), "Para fazer um aviso de viagem, " +
                "deve-se estar logado com um email autorizado");

        Optional<Cartao> possivelCartao = Optional.ofNullable(
                entityManager.find(Cartao.class, idCartao)); //2
        if (possivelCartao.isEmpty()) //3
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        logger.info("Cartão encontrado: {}", possivelCartao.get().toString());

        String emailDonoCartao = possivelCartao.get().emailDonocartao();
        if (!emailDonoCartao.equals(emailLogado.get())) //4
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        logger.info("Dono do cartão: {}", emailDonoCartao);

        String userAgent = httpRequest.getHeader("user-agent");
        String ipClient = httpRequest.getRemoteAddr();

        AvisoViagem novoAvisoViagem = request.toAvisoViagem(possivelCartao.get(),
                userAgent, ipClient); //5

        logger.info("Solicitação de nova viagem: {}", novoAvisoViagem.toString());

        Map<String, String> dadosViagem = new HashMap<>();
        dadosViagem.put("destino", request.getDestinoViagem());
        dadosViagem.put("validoAte", request.getDataTerminoViagem().toString());

        avisarViagem.notificarAvisoViagem(possivelCartao.get().getNumero(), dadosViagem); //6

        novoAvisoViagem.atualizarStatusViagem();

        entityManager.persist(novoAvisoViagem);

        return ResponseEntity.created(builder.path("/api/cartoes/{id}/aviso-viagem/{id}")
        .buildAndExpand(possivelCartao.get().getId(), novoAvisoViagem.getId()).toUri()).build();
    }

}
