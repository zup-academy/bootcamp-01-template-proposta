package com.proposta.solicitacaoderecuperacaodesenha;

import com.proposta.criacaocartao.Cartao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.net.URI;

@RestController
@RequestMapping("/recuperarsenha")
public class RecuperacaoSenhaController {

    @Autowired
    EntityManager manager;

    @PostMapping("/{idCartao}")
    @Transactional
    public ResponseEntity<?> bloqueia(@PathVariable String idCartao, @RequestHeader(name = "User-Agent") String user,
                                      UriComponentsBuilder builder) {

        //1
        Cartao cartao = manager.find(Cartao.class, idCartao);
        if (cartao == null) {
            return ResponseEntity.badRequest().body("Cartão inválido.");
        }

        String ip = ((WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getRemoteAddress();

        //2
        RecuperarSenha recuperarSenha = new RecuperarSenha(user, ip);
        manager.persist(recuperarSenha);
        cartao.recuperarSenha(recuperarSenha);
        manager.merge(cartao);

        URI uriCreated = builder.path("/bloqueio/{id}").build(recuperarSenha.getId());
        return ResponseEntity.created(uriCreated).build();
    }
}