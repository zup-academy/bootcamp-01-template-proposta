package io.github.evertocnsouza.rest.controller;

import io.github.evertocnsouza.domain.entity.Cartao;
import io.github.evertocnsouza.domain.enums.TipoCarteira;
import io.github.evertocnsouza.domain.service.CarteiraService;
import io.github.evertocnsouza.rest.dto.request.CarteiraRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/cartoes")
public class CarteiraController {
    //6 PCI's;

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private CarteiraService carteiraService;
    //PCI 1;

    private Logger logger = LoggerFactory.getLogger(CarteiraController.class);

    @PostMapping("/{idCartao}/carteiras/samsungpay")
    @Transactional
    public ResponseEntity CartaoMaisSamsungPay(@PathVariable Long idCartao, @RequestBody @Valid CarteiraRequest request, //PCI 2;
                                                      UriComponentsBuilder builder) {

        return processarSolicitacao(TipoCarteira.SAMSUNG_PAY, idCartao, request, builder);
        //PCI 3;
    }

    @PostMapping("/{idCartao}/carteiras/paypal")
    @Transactional
    public ResponseEntity CartaoMaisPayPal(@PathVariable Long idCartao,
                                                  @RequestBody @Valid CarteiraRequest request,
                                                  UriComponentsBuilder builder) {
        return processarSolicitacao(TipoCarteira.PAYPAL, idCartao, request, builder);
    }

    protected ResponseEntity processarSolicitacao(TipoCarteira tipoCarteira, Long idCartao,
                                                  CarteiraRequest request, UriComponentsBuilder builder) {

        Cartao cartao = manager.find(Cartao.class, idCartao);
        //PCI 4;

        //PCI 5;
        if (cartao==null){
            logger.info("Cartão não encontrado para associar com carteira.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        //PCI 6;
        if (cartao.carteiraAssociadaCartao(tipoCarteira)) {
            logger.info("Carteira já cadastrada para o cartão. Cartão: {}", cartao.getId());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

        return carteiraService.AssociarCarteiraComCartao(tipoCarteira, cartao, request.getEmail(), builder);
    }
}

