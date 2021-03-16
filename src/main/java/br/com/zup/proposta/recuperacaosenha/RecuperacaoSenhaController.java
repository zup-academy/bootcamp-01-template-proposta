package br.com.zup.proposta.recuperacaosenha;

import br.com.zup.proposta.cartao.Cartao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Optional;

@RestController
@RequestMapping("/cartoes")
public class RecuperacaoSenhaController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping("/recuperacaosenha/{idCartao}")
    @Transactional
    public ResponseEntity recuperarSenha(@PathVariable String idCartao, HttpServletRequest httpRequest,
                                         UriComponentsBuilder uri) {
        Optional<Cartao> possivelCartao = Optional.ofNullable(manager.find(Cartao.class, idCartao));

        if (possivelCartao.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Cartao cartao = possivelCartao.get();

        RecuperacaoSenha recuperacaoSenha = new RecuperacaoSenha(httpRequest.getRemoteAddr(),
                httpRequest.getHeader("User-Agent"), cartao);

        manager.persist(recuperacaoSenha);

        return ResponseEntity.created(uri.path("/recuperacoes/{id}")
                .buildAndExpand(recuperacaoSenha.getId()).toUri()).build();
    }
}
