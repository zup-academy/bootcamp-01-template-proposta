package br.com.zup.proposta.carteira;

import br.com.zup.proposta.cartao.Cartao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;

@RestController
@RequestMapping("/cartoes")
public class CarteriaController {

    @PersistenceContext
    private EntityManager manager;
    @Autowired
    private CarteiraService carteiraService;

    @PostMapping("/carteiras/paypal/{idCartao}")
    @Transactional
    public ResponseEntity associarCarteiraPaypal(@PathVariable String idCartao,
                                                 @RequestBody CarteiraRequest request, UriComponentsBuilder uri) {
        return processarAssociacaoCarteira(TipoCarteira.PAYPAL, idCartao, request, uri);
    }

    @PostMapping("/carteiras/samsungpay/{idCartao}")
    @Transactional
    public ResponseEntity associarCarteiraSamsungPay(@PathVariable String idCartao,
                                                 @RequestBody CarteiraRequest request, UriComponentsBuilder uri) {
        return processarAssociacaoCarteira(TipoCarteira.SAMSUNG_PAY, idCartao, request, uri);
    }

    public ResponseEntity processarAssociacaoCarteira(TipoCarteira tipoCarteira, String idCartao,
                                                      CarteiraRequest request, UriComponentsBuilder uri) {
        Optional<Cartao> possivelCartao = Optional.ofNullable(manager.find(Cartao.class, idCartao));

        if (possivelCartao.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Cartao cartao = possivelCartao.get();

        if (cartao.verificarSeCartaoJaPossuiCarteiraAssociada(tipoCarteira)) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

        return carteiraService.processarAssociacao(cartao, request.getEmail(), tipoCarteira, uri);
    }
}
