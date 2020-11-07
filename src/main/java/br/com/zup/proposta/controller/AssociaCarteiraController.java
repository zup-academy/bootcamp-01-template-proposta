package br.com.zup.proposta.controller;

import br.com.zup.proposta.dto.request.CarteiraRequest;
import br.com.zup.proposta.enums.TipoCarteira;
import br.com.zup.proposta.model.Cartao;
import br.com.zup.proposta.service.AssociaCarteiraService;
import br.com.zup.proposta.utils.Error;
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
public class AssociaCarteiraController {

    private EntityManager entityManager;

    private AssociaCarteiraService associaCarteiraService;

    private Logger logger = LoggerFactory.getLogger(AssociaCarteiraController.class);

    public AssociaCarteiraController(EntityManager entityManager, AssociaCarteiraService associaCarteiraService) {
        this.entityManager = entityManager;
        this.associaCarteiraService = associaCarteiraService;
    }

    @PostMapping("/{cartaoID}/carteiras/paypal")
    @Transactional
    public ResponseEntity vincularCartaoPaypal(@PathVariable UUID cartaoID, @RequestBody @Valid CarteiraRequest carteiraRequest, UriComponentsBuilder builder){
        return processarAssociacao(TipoCarteira.PAYPAL, cartaoID, carteiraRequest, builder);
    }

    @PostMapping("/{cartaoID}/carteiras/samsungpay")
    @Transactional
    public ResponseEntity vincularCartaoSamsungPay(@PathVariable UUID cartaoID, @RequestBody @Valid CarteiraRequest carteiraRequest, UriComponentsBuilder builder) {
        return processarAssociacao(TipoCarteira.SAMSUNG_PAY, cartaoID, carteiraRequest, builder);
    }

    protected ResponseEntity processarAssociacao(TipoCarteira tipoCarteira, UUID cartaoID, CarteiraRequest carteiraRequest, UriComponentsBuilder builder) {

        Optional<Cartao> cartaoProcurado = Optional.ofNullable(entityManager.find(Cartao.class, cartaoID));

        if(cartaoProcurado.isEmpty()){
            logger.info("[ASSOCIAÇÃO DE CARTEIRA] Cartão não encontrado {}", cartaoID);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error(Arrays.asList("Cartão não encontrado")));
        }

        Cartao cartao = cartaoProcurado.get();

        if(cartao.associacaoJaExisteComCartao(tipoCarteira)){
            logger.info("[ASSOCIAÇÃO DE CARTEIRA] Carteira já cadastrada para o cartão {}", cartao.getId());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new Error(Arrays.asList("Cartão já associado")));
        }

        return associaCarteiraService.processarAssociacaoComCartao(tipoCarteira, cartao, carteiraRequest.getEmail(), builder);
    }
}
