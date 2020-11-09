package com.proposta.cadastraravisoviagem;

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
@RequestMapping("/avisoviagens")
public class AvisoViagemController {

    @Autowired
    EntityManager manager;

    @PostMapping("/{idCartao}")
    @Transactional
    public ResponseEntity<?> avisoViagem(@PathVariable String idCartao, @RequestHeader(name = "User-Agent") String user,
                                         @RequestBody AvisoViagemRequest request , UriComponentsBuilder builder) {

        //1
        Cartao cartao = manager.find(Cartao.class, idCartao);
        if (cartao == null) {
            return ResponseEntity.badRequest().body("Cartão inválido.");
        }

        String ip = ((WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getRemoteAddress();

        AvisoViagem avisoViagem = request.toModel(user, ip);
        manager.persist(avisoViagem);
        cartao.adicionarAvisos(avisoViagem);
        manager.merge(cartao);

        URI uriCreated = builder.path("/avisoviagens/{id}").build(avisoViagem.getId());
        return ResponseEntity.created(uriCreated).build();
    }
}