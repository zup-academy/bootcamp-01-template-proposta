package br.com.proposta.resources;

import br.com.proposta.dtos.requests.AssociarCarteiraRequest;
import br.com.proposta.dtos.responses.AssociaCarteiraResponse;
import br.com.proposta.entidades.Aviso;
import br.com.proposta.entidades.Cartao;
import br.com.proposta.entidades.Carteira;
import br.com.proposta.entidades.Enums.StatusCarteira;
import br.com.proposta.repositories.CartaoRepository;
import br.com.proposta.repositories.CarteiraRepository;
import br.com.proposta.integracoes.IntegracaoApiCartoes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/api/carteiras")
public class CarteiraResource {

    /* total de pontos = 8 */

    /* @complexidade - acoplamento contextual */
    private final IntegracaoApiCartoes integracaoApiCartoes;

    /* @complexidade - acoplamento contextual */
    private final CarteiraRepository carteiraRepository;

    /* @complexidade - acoplamento contextual */
    private final CartaoRepository cartaoRepository;

    private final Logger logger = LoggerFactory.getLogger(Carteira.class);


    public CarteiraResource(IntegracaoApiCartoes integracaoApiCartoes, CarteiraRepository carteiraRepository,
                            CartaoRepository cartaoRepository) {
        this.integracaoApiCartoes = integracaoApiCartoes;
        this.carteiraRepository = carteiraRepository;
        this.cartaoRepository = cartaoRepository;
    }


    @PostMapping("/{numeroCartao}")
    public ResponseEntity<?> associa(@PathVariable String numeroCartao,  /* @complexidade - classe criada no projeto */
                                     @RequestBody AssociarCarteiraRequest associarCarteiraRequest, UriComponentsBuilder uriComponentsBuilder){


        /* @complexidade - utilizando classe criada no projeto */
        Cartao cartao = cartaoRepository.findByNumero(numeroCartao);

        /* @complexidade - utilizando classe criada no projeto */
        ResponseEntity<AssociaCarteiraResponse> response =
                integracaoApiCartoes.associarCarteira(numeroCartao, associarCarteiraRequest);


        /* @complexidade - if */
        if(response.getStatusCode() == HttpStatus.OK){

            /* @complexidade - utilizando classe criada no projeto */
            StatusCarteira status = StatusCarteira.valueOf(response.getBody().getResultado());

            /* @complexidade - utilizando classe criada no projeto */
            Carteira carteira = new Carteira(associarCarteiraRequest.getCarteira(), status, cartao);

            carteiraRepository.save(carteira);

            logger.info("Cartão associado à carteira com identificador = {}", carteira.getId());

            return ResponseEntity
                    .created(uriComponentsBuilder.path("/api/carteiras/{id}")
                            .buildAndExpand(carteira.getId()).toUri()).build();

        }


        logger.info("Cartão de número {} não foi encontrado", numeroCartao);

        return ResponseEntity.notFound().build();


    }
}
