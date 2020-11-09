package com.proposta.bloqueiodecartao;

import com.proposta.criacaocartao.Cartao;
import com.proposta.feign.ApiCartaoCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
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

    @Autowired
    private ApiCartaoCliente apiCartaoCliente;

    @Value("${spring.application.name}")
    private String nomeDaAplicacao;

    @PostMapping("/{idCartao}")
    @Transactional
    public ResponseEntity<?> bloqueia(@PathVariable String idCartao, @RequestHeader(name = "User-Agent") String user,
                                      UriComponentsBuilder builder) {

        //1
        Cartao cartao = manager.find(Cartao.class, idCartao);
        if (cartao == null) {
            return ResponseEntity.badRequest().body("Cartão inválido.");
        }

        String ip = ((WebAuthenticationDetails)SecurityContextHolder.getContext().getAuthentication().getDetails()).getRemoteAddress();

        //2
        Bloqueios bloqueios = new Bloqueios(user, ip);
        manager.persist(bloqueios);
        cartao.ativarBloqueio(bloqueios);
        manager.merge(cartao);

        //3
        BloqueioResponse bloqueioResponse = apiCartaoCliente.bloquearCartao(cartao.getId(), new BloqueioRequest(nomeDaAplicacao));

        if (bloqueioResponse.getResultado().equals("BLOQUEADO")) {
            cartao.setStatus(StatusCartao.BLOQUEADO);
        }

        URI uriCreated = builder.path("/bloqueio/{id}").build(bloqueios.getId());
        return ResponseEntity.created(uriCreated).build();
    }
}
