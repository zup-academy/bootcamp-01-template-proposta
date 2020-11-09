package br.com.zup.cartaoproposta.controllers;

import br.com.zup.cartaoproposta.entities.cartao.Cartao;
import br.com.zup.cartaoproposta.entities.cartao.biometria.Biometria;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.net.URI;

/**
 * Contagem de carga intrínseca da classe: 5
 */

@RestController
@RequestMapping("/cartoes/biometria")
public class BiometriaController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping("/{id}")
    @Transactional
    //1
    public ResponseEntity<String> cadastroBiometria(@PathVariable("id") String idCartao, String biometria, UriComponentsBuilder uriComponentsBuilder){
        //1
        Cartao cartao = manager.find(Cartao.class, idCartao);

        //1
        if(cartao == null) {
            return ResponseEntity.notFound().build();
        }

        //1
        if(biometria == null || !Base64.isBase64(biometria)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Biometria inválida");
        }

        //1
        Biometria biometriaP = new Biometria(biometria, cartao);

        manager.persist(biometriaP);

        URI link = uriComponentsBuilder.path("/biometria/{id}").buildAndExpand(biometriaP.getId()).toUri();
        return ResponseEntity.created(link).build();
    }
}
