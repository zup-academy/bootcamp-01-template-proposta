package br.com.cartao.proposta.controller;

import br.com.cartao.proposta.domain.dto.CarteiraDigitalDto;
import br.com.cartao.proposta.domain.enums.CarteiraDigitalTipo;
import br.com.cartao.proposta.domain.enums.EstadoAvisoLegado;
import br.com.cartao.proposta.domain.model.Cartao;
import br.com.cartao.proposta.domain.model.CarteiraDigital;
import br.com.cartao.proposta.domain.request.PaypalAssociacaoRequest;
import br.com.cartao.proposta.domain.response.CarteiraDigitalResponseDto;
import br.com.cartao.proposta.repository.CarteiraDigitalRepository;
import br.com.cartao.proposta.service.AssociacaoCarteiraCartao;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.Optional;

/**
 * Carga intrínseca máxima permitida - 7
 * Carga intrínseca da classe - 11
 */

@RestController
@RequestMapping("/v1/cartoes")
public class PaypalAssociacaoController {

    private static Logger logger = LoggerFactory.getLogger(PaypalAssociacaoController.class);
    // +1
    private static EstadoAvisoLegado estadoAvisoLegado= EstadoAvisoLegado.AVISADO;
    // +1
    private static CarteiraDigitalTipo carteiraDigitalTipo = CarteiraDigitalTipo.PAYPAL;

    @PersistenceContext
    private final EntityManager manager;
    // +1
    private final AssociacaoCarteiraCartao associacaoCarteiraCartao;
    // +1
    private final CarteiraDigitalRepository carteiraDigitalRepository;

    public PaypalAssociacaoController(EntityManager manager, AssociacaoCarteiraCartao associacaoCarteiraCartao, CarteiraDigitalRepository carteiraDigitalRepository) {
        this.manager = manager;
        this.associacaoCarteiraCartao = associacaoCarteiraCartao;
        this.carteiraDigitalRepository = carteiraDigitalRepository;
    }

    @PostMapping("/{id}/carteiras/paypal")
    // +1
    public ResponseEntity<?> associacaoCarteiraPaypal(@PathVariable(value = "id", required = true) String idCartao,
                                                      @RequestBody @Valid PaypalAssociacaoRequest paypalAssociacaoRequest,
                                                      UriComponentsBuilder uriComponentsBuilder) throws JsonProcessingException {
        logger.info("Requisição para associação de carteira digital para Paypal recebida. Id do cartão: {}, requisição: {}",idCartao, paypalAssociacaoRequest);
        // +1
        Cartao cartao = manager.find(Cartao.class, idCartao);
        // +1
        if (cartao == null){
            logger.info("Cartão com id : {} , não encontrado ", idCartao);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cartão id não encontrado!");
        }
        // +1
        Optional<CarteiraDigital> carteiraDigitalBuscada = carteiraDigitalRepository.findByCartaoIdAndCarteiraAndEstadoAvisoLegado(idCartao, carteiraDigitalTipo , estadoAvisoLegado);
        // +1
        if (carteiraDigitalBuscada.isPresent()){
            logger.info("Carteira Digital do Paypal já associada para cartao com id: {}", idCartao);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Carteira Digital do Paypal já associada para cartao!!");
        }
        // +1
        CarteiraDigitalDto carteiraDigitalDto = paypalAssociacaoRequest.toDto(cartao);

        CarteiraDigital carteiraDigitalAssociada = associacaoCarteiraCartao.associa(carteiraDigitalDto);
        // +1
        CarteiraDigitalResponseDto carteiraDigitalResponseDto = new CarteiraDigitalResponseDto(carteiraDigitalAssociada);
        logger.info("Carteira Digital do Paypal associada com sucesso. Id do cartão: {}", idCartao);
        return ResponseEntity
                .created(uriComponentsBuilder.path("/v1/cartoes/{idCartao}/carteiras/paypal/{id}").buildAndExpand(idCartao,carteiraDigitalResponseDto.getId()).toUri())
                .build();
    }

}
