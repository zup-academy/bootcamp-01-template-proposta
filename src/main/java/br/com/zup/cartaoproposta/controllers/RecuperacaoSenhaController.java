package br.com.zup.cartaoproposta.controllers;

import br.com.zup.cartaoproposta.entities.cartao.Cartao;
import br.com.zup.cartaoproposta.entities.cartao.solicitacaosenha.SolicitacaoSenha;
import br.com.zup.cartaoproposta.services.cartao.AuxCartao;
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
@RequestMapping("cartoes/solicitacoes-recuperar-senha")
public class RecuperacaoSenhaController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping("/{idCartao}")
    @Transactional
    public ResponseEntity<String> cadastroRecuperacaoSenha(@PathVariable("idCartao") String idCaratao, UriComponentsBuilder uriComponentsBuilder, @AuthenticationPrincipal Jwt principal, HttpServletRequest request) {
        //1
        Cartao cartao = manager.find(Cartao.class, idCaratao);

        //1
        if(cartao == null) {
            return ResponseEntity.notFound().build();
        }

        String idUser = principal.getClaimAsString("sub");
        //1
        String ipAddress = AuxCartao.getIpAdress(request);

        //1
        SolicitacaoSenha solicitacaoSenha = new SolicitacaoSenha(ipAddress, idUser, cartao);
        manager.persist(solicitacaoSenha);

        URI link = uriComponentsBuilder.path("/cartoes/solicitacao-recuperar-senha/{id}").buildAndExpand(solicitacaoSenha.getId()).toUri();
        return ResponseEntity.created(link).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> buscaRecuperacaoSenha(@PathVariable("id") String id) {
        SolicitacaoSenha solicitacaoSenha = manager.find(SolicitacaoSenha.class, id);

        //1
        if(solicitacaoSenha == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(solicitacaoSenha.toString());
    }

}
