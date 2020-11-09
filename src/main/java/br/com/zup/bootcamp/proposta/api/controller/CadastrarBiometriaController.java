package br.com.zup.bootcamp.proposta.api.controller;

import br.com.zup.bootcamp.proposta.api.dto.RequestBiometriaDto;
import br.com.zup.bootcamp.proposta.domain.repository.CartaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/cartoes")
public class CadastrarBiometriaController {

    private final Logger logger = LoggerFactory.getLogger(CadastrarBiometriaController.class);
    @Autowired      //1
    private CartaoRepository cartaoRepository;

    @PostMapping("/{idCartao}/biometria")                                                   //1
    public ResponseEntity<?> cadastrarBiometria(@PathVariable String idCartao, @RequestBody @Valid RequestBiometriaDto request, UriComponentsBuilder uri){
        logger.info("inicinado processo de cadastro do cartão com id {}", idCartao);

        var cartao = cartaoRepository.findById(idCartao);
        var biometria = request.toEntity();
        //1
        if(cartao.isEmpty()){
            logger.warn("Cartão com id {} não encontrado.", idCartao);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        cartao.get().adicionarBiometriaNoCartao(biometria.getFingerprint());
        cartaoRepository.save(cartao.get());

        return ResponseEntity.created(uri.path("/cartoes/biometrias/{id}").buildAndExpand(cartao.get().getId()).toUri()).build();
    }
}
