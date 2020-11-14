package com.github.marcoscoutozup.proposta.carteira;

import com.github.marcoscoutozup.proposta.cartao.Cartao;
import com.github.marcoscoutozup.proposta.carteira.enums.TipoCarteira;
import com.github.marcoscoutozup.proposta.exception.StandardError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/cartoes")
public class CarteiraController {

    private EntityManager entityManager;
    private CarteiraService carteiraService;
    private Logger logger = LoggerFactory.getLogger(CarteiraController.class);

    public CarteiraController(EntityManager entityManager, CarteiraService carteiraService) {
        this.entityManager = entityManager;
        this.carteiraService = carteiraService;
    }

    @PostMapping("/{idCartao}/carteiras/samsungpay")
    @Transactional
    public ResponseEntity vincularCartaoComSamsungPay(@PathVariable UUID idCartao,
                                                      @RequestBody @Valid CarteiraRequest carteiraRequest, //1
                                                      @RequestHeader(name = "Authorization") String token,
                                                      UriComponentsBuilder uri){
                                    //2
        return processarSolicitacao(TipoCarteira.SAMSUNG_PAY, idCartao, carteiraRequest, token, uri);
    }

    @PostMapping("/{idCartao}/carteiras/paypal")
    @Transactional
    public ResponseEntity vincularCartaoComPayPal(@PathVariable UUID idCartao,
                                                  @RequestBody @Valid CarteiraRequest carteiraRequest,
                                                  @RequestHeader(name = "Authorization") String token,
                                                  UriComponentsBuilder uri){
        return processarSolicitacao(TipoCarteira.PAYPAL, idCartao, carteiraRequest, token, uri);
    }

    protected ResponseEntity processarSolicitacao(TipoCarteira tipoCarteira, UUID idCartao, CarteiraRequest carteiraRequest, String token, UriComponentsBuilder uri){
                    //3
        Optional<Cartao> cartaoProcurado = Optional.ofNullable(entityManager.find(Cartao.class, idCartao));

        //4
        if(cartaoProcurado.isEmpty()){
            logger.info("[ASSOCIAÇÃO DE CARTEIRA] Cartão não encontrado. Id: {}", idCartao);
                                                                            //4
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StandardError(Arrays.asList("Cartão não encontrado")));
        }

        Cartao cartao = cartaoProcurado.get();

        //5
        if(!cartao.verificarSeOEmailDoTokenEOMesmoDoCartao(token)){
            logger.warn("[CADASTRO DE AVISO] O email não token não corresponde ao proprietário do cartão. Id: {}", idCartao);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new StandardError(Arrays.asList("Cartão não pertencente ao solicitante")));
        }

        //6
        if(cartao.verificarSeJaExisteAssociacaoDaCarteiraComOCartao(tipoCarteira)){
            logger.info("[ASSOCIAÇÃO DE CARTEIRA] Carteira já cadastrada para o cartão. Cartão: {}", cartao.getId());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new StandardError(Arrays.asList("O cartão já está associado a carteira")));
        }

        return carteiraService.processarAssociacaoDaCarteiraComCartao(tipoCarteira, cartao, carteiraRequest.getEmail(), uri);
    }



}
