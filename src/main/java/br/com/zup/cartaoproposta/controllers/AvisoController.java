package br.com.zup.cartaoproposta.controllers;

import br.com.zup.cartaoproposta.entities.cartao.Cartao;
import br.com.zup.cartaoproposta.entities.cartao.aviso.AvisoCartao;
import br.com.zup.cartaoproposta.entities.cartao.aviso.AvisoNovoRequest;
import br.com.zup.cartaoproposta.services.cartao.AuxCartao;
import br.com.zup.cartaoproposta.services.cartao.AvisarCartao;
import io.opentracing.Span;
import io.opentracing.Tracer;
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
import javax.validation.Valid;
import java.net.URI;

/**
 * Contagem de carga intrínseca da classe: 7
 */

@RestController
@RequestMapping("/cartoes/avisos")
public class AvisoController {

    @PersistenceContext
    EntityManager manager;

    @Autowired
    //1
    AvisarCartao avisarCartao;

    private final Tracer tracer;

    public AvisoController(Tracer tracer) {
        this.tracer = tracer;
    }

    @PostMapping("/{idCartao}")
    @Transactional
    //1
    public ResponseEntity<String> cadastroAviso(@PathVariable("idCartao") String id, @Valid @RequestBody AvisoNovoRequest novoAviso, UriComponentsBuilder uriComponentsBuilder, @AuthenticationPrincipal Jwt principal, HttpServletRequest request) {
        //1
        Cartao cartao = manager.find(Cartao.class, id);

        //1
        if(cartao == null) {
            return ResponseEntity.notFound().build();
        }

        String nCartao = cartao.getIdLegado();

        /*
            Realiza o aviso da viagem do cartão
            Em caso de erro no aviso ocorre um throw new ResponseStatusException
         */
        avisarCartao.avisarCartaoLegado(nCartao, novoAviso);

        String idUser = principal.getClaimAsString("sub");
        //1
        String ipAddress = AuxCartao.getIpAdress(request);

        Span activeSpan = tracer.activeSpan();
        activeSpan.setTag("user.id", idUser);
        activeSpan.setTag("user.email", principal.getClaimAsString("email"));
        activeSpan.log("Aviso de viagem cadastrado no legado.");
        activeSpan.log("Aviso de viagem cadastrado no cartao-branco-proposta.");

        //1
        AvisoCartao avisoCartao = new AvisoCartao(novoAviso.getValidoAte(),novoAviso.getDestino(), ipAddress, idUser, cartao);

        manager.persist(avisoCartao);

        URI link = uriComponentsBuilder.path("/cartoes/avisos/{id}").buildAndExpand(avisoCartao.getId()).toUri();
        return ResponseEntity.created(link).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> buscaAviso(@PathVariable("id") String id) {
        AvisoCartao avisoCartao = manager.find(AvisoCartao.class, id);

        //1
        if (avisoCartao == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(avisoCartao.toString());
    }
}
