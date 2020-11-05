package com.proposta.bloqueiodecartao;

import com.proposta.criacaocartao.Cartao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.net.URI;

@RestController
@RequestMapping("/bloqueios")
public class BloqueioCartaoController {

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

        //2
        Bloqueios bloqueios = new Bloqueios(user, "ipfake");
        manager.persist(bloqueios);
        cartao.ativarBloqueio(bloqueios);
        manager.merge(cartao);

        URI uriCreated = builder.path("/bloqueio/{id}").build(bloqueios.getId());

        return ResponseEntity.created(uriCreated).build();
    }
}
