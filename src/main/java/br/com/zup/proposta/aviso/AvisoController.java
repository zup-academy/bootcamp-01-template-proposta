package br.com.zup.proposta.aviso;

import br.com.zup.proposta.cartao.Cartao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/cartoes")
public class AvisoController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping("/aviso/{idCartao}")
    @Transactional
    public ResponseEntity cadastrarAviso(@PathVariable String idCartao, @RequestBody @Valid AvisoRequest request,
                                         HttpServletRequest httpRequest, UriComponentsBuilder uri) {
        Optional<Cartao> possivelCartao = Optional.ofNullable(manager.find(Cartao.class, idCartao));

        if (possivelCartao.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Cartao cartao = possivelCartao.get();

        Aviso aviso = request.toModel(httpRequest);
        manager.persist(aviso);

        cartao.addAviso(aviso);
        manager.merge(cartao);

        return ResponseEntity.created(uri.path("/avisos/{id}").buildAndExpand(aviso.getId()).toUri()).build();
    }
}
