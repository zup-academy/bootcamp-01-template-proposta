package br.com.itau.cartaobrancoproposta.controller;

import br.com.itau.cartaobrancoproposta.component.TransacaoDados;
import br.com.itau.cartaobrancoproposta.model.Cartao;
import br.com.itau.cartaobrancoproposta.model.CarteiraDigital;
import br.com.itau.cartaobrancoproposta.model.CarteiraDigitalRequest;
import br.com.itau.cartaobrancoproposta.service.CarteiraDigitalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class CarteiraDigitalController {

    Logger logger = LoggerFactory.getLogger(CarteiraDigitalController.class);
//1
    private final TransacaoDados transacaoDados;
//1
    private final CarteiraDigitalService carteiraDigitalService;

    public CarteiraDigitalController(TransacaoDados transacaoDados, CarteiraDigitalService carteiraDigitalService) {
        this.transacaoDados = transacaoDados;
        this.carteiraDigitalService = carteiraDigitalService;
    }

    @PostMapping("/v1/cartoes/{id}/carteiras")
    public ResponseEntity<?> associaAoCartao(@PathVariable("id") String numeroCartao, @Valid @RequestBody CarteiraDigitalRequest carteiraDigitalRequest,
                                             UriComponentsBuilder uriComponentsBuilder) { //1
        Cartao cartao = transacaoDados.buscaPorNumeroDoCartao(numeroCartao); //1

        if (cartao == null) { //1
            logger.error("Cartão não foi encontrado.");
            return ResponseEntity.notFound().build();
        }

        CarteiraDigital carteiraDigital = carteiraDigitalRequest.toModel(); //1

        cartao.verificaCarteiraJaCadastrada(carteiraDigital);

        carteiraDigitalService.cadastra(numeroCartao, carteiraDigital);

        transacaoDados.salva(carteiraDigital);
        logger.info("Carteira digital id={} emissor={} associadoEm={} criada com sucesso!", carteiraDigital.getId(), carteiraDigital.getEmissor(), carteiraDigital.getAssociadaEm());

        cartao.carregaCarteira(carteiraDigital);
        transacaoDados.atualiza(cartao);
        logger.info("Carteira digital id={} emissor={} associadoEm={} foi atrelado ao cartão com final {} com sucesso!",
                carteiraDigital.getId(), carteiraDigital.getEmissor(), carteiraDigital.getAssociadaEm(), cartao.getNumeroCartao().substring(24));

        URI uri = uriComponentsBuilder.path("/v1/carteiras/{id}").buildAndExpand(carteiraDigital.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
