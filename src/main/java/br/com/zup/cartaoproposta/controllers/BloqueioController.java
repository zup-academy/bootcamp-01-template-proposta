package br.com.zup.cartaoproposta.controllers;

import br.com.zup.cartaoproposta.entities.cartao.Cartao;
import br.com.zup.cartaoproposta.entities.cartao.bloqueio.BloqueioCartao;
import br.com.zup.cartaoproposta.services.cartao.AuxCartao;
import br.com.zup.cartaoproposta.services.cartao.BloquearCartao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.net.URI;

/**
 * Contagem de carga intrínseca da classe: 5
 */

@RestController
@RequestMapping("/cartoes/bloqueio")
public class BloqueioController {

    @Autowired
    //1
    BloquearCartao bloquearCartao;

    @PersistenceContext
    EntityManager manager;

    @PostMapping("/{idCartao}")
    @Transactional
    public ResponseEntity<String> cadastroBloqueio(@PathVariable("idCartao") String id, UriComponentsBuilder uriComponentsBuilder, @AuthenticationPrincipal Jwt principal, HttpServletRequest request){

        //1
        Cartao cartao = manager.find(Cartao.class, id);

        //1
        if(cartao == null) {
            return ResponseEntity.notFound().build();
        }

        String nCartao = cartao.getIdLegado();

        /*
            Realiza o bloqueio do cartão
            Em caso de erro no bloqueio ocorre um throw new ResponseStatusException
         */
        bloquearCartao.bloquearCartaoLegado(nCartao);

        String idUser = principal.getClaimAsString("sub");
        String ipAddress = AuxCartao.getIpAdress(request);

        //1
        BloqueioCartao bloqueioCartao = new BloqueioCartao(true, ipAddress, idUser, cartao);

        cartao.bloquearCartao();

        manager.persist(bloqueioCartao);

        URI link = uriComponentsBuilder.path("/cartoes/bloqueio/{id}").buildAndExpand(bloqueioCartao.getId()).toUri();
        return ResponseEntity.created(link).build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<String> buscaBloqueio(@PathVariable("id") String id){
        BloqueioCartao bloqueioCartao = manager.find(BloqueioCartao.class, id);
        //1
        if (bloqueioCartao == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(bloqueioCartao.toString());

    }
}
