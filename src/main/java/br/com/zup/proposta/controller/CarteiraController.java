package br.com.zup.proposta.controller;

import br.com.zup.proposta.model.Cartao;
import br.com.zup.proposta.model.Carteira;
import br.com.zup.proposta.model.enums.TipoCarteira;
import br.com.zup.proposta.service.AssociarCarteira;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/cartoes")
public class CarteiraController {

    private EntityManager entityManager;
    private AssociarCarteira associarCarteira; //1
    private Logger logger = LoggerFactory.getLogger(CarteiraController.class);

    public CarteiraController(EntityManager entityManager, AssociarCarteira associarCarteira) {
        this.entityManager = entityManager;
        this.associarCarteira = associarCarteira;
    }

    @PostMapping("{id}/carteiras/paypal")
    @Transactional
    public ResponseEntity adicionarCarteiraPayPal(@PathVariable("id") UUID idCartao,
                                          UriComponentsBuilder builder,
                                          @AuthenticationPrincipal Jwt jwt){
        return associarCarteira(idCartao, builder, jwt, TipoCarteira.PAYPAL);
    }

    @PostMapping("{id}/carteiras/samsung")
    @Transactional
    public ResponseEntity adicionarCarteiraSamsung(@PathVariable("id") UUID idCartao,
                                                  UriComponentsBuilder builder,
                                                  @AuthenticationPrincipal Jwt jwt){
        return associarCarteira(idCartao, builder, jwt, TipoCarteira.SAMSUNGPAY);
    }

    private ResponseEntity associarCarteira(UUID idCartao, UriComponentsBuilder builder,
                                           Jwt jwt, TipoCarteira tipoCarteira){

        Optional<String> emailLogado = Optional.ofNullable(jwt.getClaim("email"));
        Assert.isTrue(emailLogado.isPresent(), "Para associar uma carteira ao cartão, " +
                "deve-se estar logado com um email autorizado");

        Optional<Cartao> possivelCartao = Optional
                .ofNullable(entityManager.find(Cartao.class, idCartao)); //2

        if (possivelCartao.isEmpty()) //3
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        logger.info("Informações do cartao: {}", possivelCartao.get().toString());

        String emailDonoCartao = possivelCartao.get().emailDonocartao();

        if (!emailDonoCartao.equals(emailLogado.get())) //4
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);

        Carteira novaCarteira = new Carteira(possivelCartao.get(), emailDonoCartao,
                tipoCarteira); //5

        if (!possivelCartao.get().verificaAssociacaoCarteira(tipoCarteira)) //6
        {
            Map<String, String> dadosCarteira = new HashMap<>();
            dadosCarteira.put("email", emailDonoCartao);
            dadosCarteira.put("carteira", tipoCarteira.toString());

            associarCarteira.notificarAssociacaoCarteira(
                    novaCarteira.numeroCartao(), dadosCarteira);

            entityManager.persist(novaCarteira);

            logger.info("Nova carteira: {}", novaCarteira.toString());
        }

        return ResponseEntity.created(builder.path("/api/cartoes/{id}/carteiras/{id}")
                .buildAndExpand(possivelCartao.get().getNumero(), novaCarteira.getId())
                .toUri()).build();
    }

}
