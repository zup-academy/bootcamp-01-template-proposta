package br.com.proposta.controllers;

import br.com.proposta.dtos.requests.AssociaCarteiraRequest;
import br.com.proposta.dtos.responses.AssociaCarteiraResponse;
import br.com.proposta.models.Cartao;
import br.com.proposta.models.Carteira;
import br.com.proposta.models.Enums.StatusCarteira;
import br.com.proposta.repositories.CartaoRepository;
import br.com.proposta.repositories.CarteiraRepository;
import br.com.proposta.integracoes.IntegracaoApiCartoes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/carteiras")
public class AssociaCarteiraController {

    /* total de pontos = 8 */

    /* @complexidade - classe criada no projeto */
    private final IntegracaoApiCartoes integracaoApiCartoes;

    /* @complexidade - classe criada no projeto */
    private final CarteiraRepository carteiraRepository;

    /* @complexidade - classe criada no projeto */
    private final CartaoRepository cartaoRepository;


    public AssociaCarteiraController(IntegracaoApiCartoes integracaoApiCartoes, CarteiraRepository carteiraRepository,
                                     CartaoRepository cartaoRepository) {
        this.integracaoApiCartoes = integracaoApiCartoes;
        this.carteiraRepository = carteiraRepository;
        this.cartaoRepository = cartaoRepository;
    }


    @PostMapping("/{cartaoId}")
    public ResponseEntity<?> associa(@PathVariable String cartaoId,  /* @complexidade - classe criada no projeto */
                                     @RequestBody AssociaCarteiraRequest associaCarteiraRequest){

        /* @complexidade - utilizando classe criada no projeto */
        Optional<Cartao> cartao = cartaoRepository.findById(cartaoId);

        /* @complexidade - utilizando classe criada no projeto */
        ResponseEntity<AssociaCarteiraResponse> response =
                integracaoApiCartoes.associarCarteira(cartaoId, associaCarteiraRequest);

        /* @complexidade - utilizando classe criada no projeto */
        StatusCarteira status = StatusCarteira.valueOf(response.getBody().getResultado());

        /* @complexidade - utilizando classe criada no projeto */
        Carteira carteira = new Carteira(associaCarteiraRequest.getCarteira(), status, cartao.get());

        carteiraRepository.save(carteira);

        return ResponseEntity.ok().build();

    }
}
