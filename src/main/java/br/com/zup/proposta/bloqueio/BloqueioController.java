package br.com.zup.proposta.bloqueio;

import br.com.zup.proposta.cartao.Cartao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/cartoes/bloqueio")
public class BloqueioController {
    @Autowired
    private EntityManager manager;
    @Autowired
    private BloqueioCartaoService bloqueioCartaoService;

    @PostMapping("/{idCartao}")
    @Transactional
    public ResponseEntity bloquear(@PathVariable String idCartao, HttpServletRequest httpRequest,
                                   UriComponentsBuilder uri) {

        Optional<Cartao> possivelCartao = Optional.ofNullable(manager.find(Cartao.class, idCartao));

        if (possivelCartao.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Cartao cartao = possivelCartao.get();

        Bloqueio bloqueio = new Bloqueio(httpRequest.getRemoteAddr(), httpRequest.getHeader("User-Agent"));
        manager.persist(bloqueio);
        bloqueioCartaoService.bloquearCartao(cartao);

        cartao.addBloqueio(bloqueio);
        manager.merge(cartao);

        return ResponseEntity.created(uri.path("/bloqueios/{id}").buildAndExpand(cartao.getId()).toUri()).build();
    }
}
