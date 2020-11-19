package com.proposta.inclusaonacarteira;

import com.proposta.criacaocartao.Cartao;
import com.proposta.feign.ApiCartaoCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/carteiras")
public class CarteirasController {

    @Autowired
    EntityManager manager;

    @Autowired
    private ApiCartaoCliente apiCartaoCliente;

    @PostMapping("/paypal/{idCartao}")
    @Transactional
    public ResponseEntity<?> incluiPaypal(@PathVariable String idCartao, @RequestBody @Valid CarteiraRequest request,
                                          UriComponentsBuilder builder) {

        //1
        Cartao cartao = manager.find(Cartao.class, idCartao);
        //2
        if (cartao == null) {
            return ResponseEntity.notFound().build();
        }

        //3
        if (cartao.analiseCarteira(TipoCarteira.PAYPAL)) {
            return ResponseEntity.unprocessableEntity().body("Cartão já associado.");
        }

        //4 api
        ResponseEntity retorno = apiCartaoCliente.associarCarteira(idCartao,request);

        //5
        if (retorno.getStatusCode() == HttpStatus.OK) {

            //6
            Carteiras carteiras = request.toModel(TipoCarteira.PAYPAL);
            manager.persist(carteiras);
            cartao.adicionaCarteira(carteiras);
            manager.merge(cartao);

            URI uriCreated = builder.path("/carteiras/paypal/{id}").build(carteiras.getId());
            return ResponseEntity.created(uriCreated).build();

        }
        return ResponseEntity.badRequest().body("Falha no processo.");
    }

    @PostMapping("/samsungpay/{idCartao}")
    @Transactional
    public ResponseEntity<?> incluiSamsungpay(@PathVariable String idCartao, @RequestBody @Valid CarteiraRequest request,
                                          UriComponentsBuilder builder) {
        Cartao cartao = manager.find(Cartao.class, idCartao);
        //7
        if (cartao == null) {
            return ResponseEntity.notFound().build();
        }
        //8
        if (cartao.analiseCarteira(TipoCarteira.SAMSUNG_PAY)) {
            return ResponseEntity.unprocessableEntity().body("Cartão já associado.");
        }

        ResponseEntity retorno = apiCartaoCliente.associarCarteira(idCartao,request);
        //9
        if (retorno.getStatusCode() == HttpStatus.OK) {
            Carteiras carteiras = request.toModel(TipoCarteira.SAMSUNG_PAY);
            manager.persist(carteiras);
            cartao.adicionaCarteira(carteiras);
            manager.merge(cartao);

            URI uriCreated = builder.path("/carteiras/samsungpay/{id}").build(carteiras.getId());
            return ResponseEntity.created(uriCreated).build();
        }
        return ResponseEntity.badRequest().body("Falha no processo.");
    }
}
