package br.com.zup.proposta.bloqueio;

import br.com.zup.proposta.cartao.Cartao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/cartoes/bloqueio")
public class BloqueioController {
    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private BloqueioCartaoService bloqueioCartaoService;

    @PostMapping("/{idCartao}")
    public ResponseEntity bloquear(@PathVariable String idCartao, UriComponentsBuilder uri) {

        Optional<Cartao> possivelCartao = cartaoRepository.findById(idCartao);

        if (possivelCartao.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Cartao cartao = possivelCartao.get();

        bloqueioCartaoService.bloquearCartao(cartao);

        cartaoRepository.save(cartao);

        return ResponseEntity.created(uri.path("/bloqueios/{id}").buildAndExpand(cartao.getId()).toUri()).build();
    }
}
