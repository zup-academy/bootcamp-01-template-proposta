package br.com.proposta.recursos;

import br.com.proposta.transferenciadados.requisicoes.RequisicaoAssociarCarteira;
import br.com.proposta.transferenciadados.respostas.RespostaAssociacaoCarteira;
import br.com.proposta.entidades.Cartao;
import br.com.proposta.entidades.Carteira;
import br.com.proposta.entidades.Enums.StatusCarteira;
import br.com.proposta.repositorios.CartaoRepository;
import br.com.proposta.repositorios.CarteiraRepository;
import br.com.proposta.integracoes.IntegracaoApiCartoes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/carteiras")
public class AssociaCarteiraRecurso {

    /* total de pontos = 8 */

    /* @complexidade - classe criada no projeto */
    private final IntegracaoApiCartoes integracaoApiCartoes;

    /* @complexidade - classe criada no projeto */
    private final CarteiraRepository carteiraRepository;

    /* @complexidade - classe criada no projeto */
    private final CartaoRepository cartaoRepository;


    public AssociaCarteiraRecurso(IntegracaoApiCartoes integracaoApiCartoes, CarteiraRepository carteiraRepository,
                                  CartaoRepository cartaoRepository) {
        this.integracaoApiCartoes = integracaoApiCartoes;
        this.carteiraRepository = carteiraRepository;
        this.cartaoRepository = cartaoRepository;
    }


    @PostMapping("/{cartaoId}")
    public ResponseEntity<?> associa(@PathVariable String cartaoId,  /* @complexidade - classe criada no projeto */
                                     @RequestBody RequisicaoAssociarCarteira requisicaoAssociarCarteira){

        /* @complexidade - utilizando classe criada no projeto */
        Optional<Cartao> cartao = cartaoRepository.findById(cartaoId);

        /* @complexidade - utilizando classe criada no projeto */
        ResponseEntity<RespostaAssociacaoCarteira> response =
                integracaoApiCartoes.associarCarteira(cartaoId, requisicaoAssociarCarteira);

        /* @complexidade - utilizando classe criada no projeto */
        StatusCarteira status = StatusCarteira.valueOf(response.getBody().getResultado());

        /* @complexidade - utilizando classe criada no projeto */
        Carteira carteira = new Carteira(requisicaoAssociarCarteira.getCarteira(), status, cartao.get());

        carteiraRepository.save(carteira);

        return ResponseEntity.ok().build();

    }
}
