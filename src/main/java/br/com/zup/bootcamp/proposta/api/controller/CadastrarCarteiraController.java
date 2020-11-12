package br.com.zup.bootcamp.proposta.api.controller;

import br.com.zup.bootcamp.proposta.api.dto.RequestCarteiraDto;
import br.com.zup.bootcamp.proposta.domain.repository.CartaoRepository;
import br.com.zup.bootcamp.proposta.domain.repository.CarteiraRepository;
import br.com.zup.bootcamp.proposta.domain.service.AssociaCarteiraService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;


@RestController
public class CadastrarCarteiraController {

    @Autowired      //1
    private CartaoRepository cartaoRepository;

    @Autowired
    private AssociaCarteiraService associarCarteira;
    @Autowired      //1
    private CarteiraRepository carteiraRepository;

    private final Logger logger = LoggerFactory.getLogger(CadastrarCarteiraController.class);

    @PostMapping("cartoes/{idCartao}/carteiras")
    public ResponseEntity<?> vincularCartao(@PathVariable String idCartao, @Valid @RequestBody RequestCarteiraDto request,
                                            UriComponentsBuilder uri) {

        logger.info("Processando carteira, request recebida: {}", request.toString());

        var cartao = cartaoRepository.findById(idCartao);
        var carteira = request.toEntity();
        System.out.println(carteira.toString());
        //1
        if (cartao.isEmpty()) {
            logger.warn("Cartão com id {} não encontrado.", idCartao);
            return ResponseEntity.notFound().build();
        }
        //1
        var carteiraAssociadas = carteiraRepository.findByCartaoIdAndCarteira(idCartao,carteira.getCarteira());
        if (carteiraAssociadas.isPresent() && carteiraAssociadas.get().getCarteira().equals(carteira.getCarteira())) {
            logger.warn("Cartão com id {} ja possui a empresa {} em sua carteira.", idCartao, carteira.getCarteira());
            return ResponseEntity.unprocessableEntity().build();
        }

        return associarCarteira.processaCarteira(cartao.get(), carteira,request, uri);
    }
}
