package br.com.zup.proposta.controller;

import br.com.zup.proposta.model.Cartao;
import br.com.zup.proposta.model.RecuperacaoSenha;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/cartoes")
public class RecuperarSenhaController {

    private Logger logger = LoggerFactory.getLogger(RecuperarSenhaController.class);
    private EntityManager entityManager;

    public RecuperarSenhaController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping("/{id}/recuperacao-senha")
    @Transactional
    public ResponseEntity novaRecuperacaoSenha(@PathVariable("id")UUID idCartao,
                                               HttpServletRequest request,
                                               UriComponentsBuilder builder,
                                               @AuthenticationPrincipal Jwt jwt){

        //verificar se a pessoa está logada (email)
        Optional<String> emailLogado = Optional.ofNullable(jwt.getClaim("email"));

        Assert.isTrue(emailLogado.isPresent(),
                "Para solicitar a recuperação de senha do cartão, " +
                        "deve-se estar logado com um email autorizado");

        logger.info("Email logado: {}", emailLogado.get());

        //ver se o cartão existe
        Optional<Cartao> possivelCartao = Optional.ofNullable(
                entityManager.find(Cartao.class, idCartao)); //1

        if (possivelCartao.isEmpty()) //2
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        logger.info("Cartao encontrado: {}", possivelCartao.get().toString());

        //ver se a pessoa pode recuperar senha
        String emailDonoCartao = possivelCartao.get().emailDonocartao();

        if(!emailDonoCartao.equals(emailLogado.get())) //3
            return new ResponseEntity(HttpStatus.FORBIDDEN);

        String userAgent = request.getHeader("user-agent");
        String ipClient = request.getRemoteAddr();

        RecuperacaoSenha novaRecuperacaoSenha = new RecuperacaoSenha(possivelCartao.get(),
                userAgent, ipClient); //4

        logger.info("Solicitação recuperar senha: {}", novaRecuperacaoSenha.toString());

        entityManager.persist(novaRecuperacaoSenha);

        return ResponseEntity.created(builder.path("/api/cartoes/{id}/recuperacao-senha/{id}")
        .buildAndExpand(possivelCartao.get().getId(),
                novaRecuperacaoSenha.getId()).toUri()).build();
    }

}
