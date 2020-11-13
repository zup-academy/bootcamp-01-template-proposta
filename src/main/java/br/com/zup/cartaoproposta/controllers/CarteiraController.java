package br.com.zup.cartaoproposta.controllers;

import br.com.zup.cartaoproposta.entities.cartao.Cartao;
import br.com.zup.cartaoproposta.entities.cartao.carteira.CarteiraCartao;
import br.com.zup.cartaoproposta.entities.cartao.carteira.CarteiraNovoRequest;
import br.com.zup.cartaoproposta.services.cartao.AddCarteiraCartao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

/**
 * Contagem de carga intrínseca da classe: 7
 */

@RestController
@RequestMapping("/cartoes/carteiras")
public class CarteiraController {

    @PersistenceContext
    EntityManager manager;

    @Autowired
    //1
    AddCarteiraCartao addCarteiraCartao;

    @PostMapping("/{idCartao}")
    @Transactional
    //1
    public ResponseEntity<String> cadastroCarteira(@PathVariable("idCartao") String id, @Valid @RequestBody CarteiraNovoRequest novaCarteira, UriComponentsBuilder uriComponentsBuilder) {
        //1
        Cartao cartao = manager.find(Cartao.class, id);

        //1
        if(cartao == null) {
            return ResponseEntity.notFound().build();
        }

        //1
        CarteiraCartao carteira = new CarteiraCartao(novaCarteira.getEmail(), novaCarteira.getCarteira(), cartao);

        //1
        if (cartao.possuiCarteira(carteira)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Carteira já existente.");
        }

        String nCartao = cartao.getIdLegado();

        /*
            Realiza a adição da carteira no cartão
            Em caso de erro na adição ocorre um throw new ResponseStatusException
         */
        addCarteiraCartao.addCarteiraCartaoLegado(nCartao, novaCarteira);

        manager.persist(carteira);

        URI link = uriComponentsBuilder.path("/cartoes/carteiras/{id}").buildAndExpand(carteira.getId()).toUri();
        return ResponseEntity.created(link).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> buscaCarteeira(@PathVariable("id") String id) {
        CarteiraCartao carteiraCartao = manager.find(CarteiraCartao.class, id);

        //1
        if (carteiraCartao == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(carteiraCartao.toString());
    }
}
