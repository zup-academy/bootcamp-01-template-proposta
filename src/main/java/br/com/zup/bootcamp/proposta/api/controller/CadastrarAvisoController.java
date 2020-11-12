package br.com.zup.bootcamp.proposta.api.controller;

import br.com.zup.bootcamp.proposta.api.dto.RequestAvisoDto;
import br.com.zup.bootcamp.proposta.api.externalsystem.LegadoSistemaCartao;
import br.com.zup.bootcamp.proposta.domain.repository.AvisoRepository;
import br.com.zup.bootcamp.proposta.domain.repository.CartaoRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class CadastrarAvisoController {

    private final Logger logger = LoggerFactory.getLogger(CadastrarAvisoController.class);

    @Autowired      //1
    private CartaoRepository cartaoRepository;

    @Autowired      //1
    private LegadoSistemaCartao sistemaCartao;

    @Autowired      //1
    private AvisoRepository avisoRepository;


    @PostMapping("cartoes/{idCartao}/avisos")
    public ResponseEntity<?> cadastrarAvisoDeViagem(@PathVariable String idCartao,  //1
                                                    @RequestBody @Valid RequestAvisoDto request, HttpServletRequest httpRequest,
                                                    UriComponentsBuilder uri){
        logger.info("Processando aviso viagem, request recebida: {}", request.toString());

        var cartao = cartaoRepository.findById(idCartao);
        //1
        if(cartao.isEmpty()){
            logger.warn("Cartão com id {} não encontrado.", idCartao);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        var aviso = request.toEntity(httpRequest);
        //1
        try {
            logger.info("Solicitando aviso de viagem para o sistema de cartões do cartão id: {}", idCartao);
            sistemaCartao.solicitarAvisoDeViagem(cartao.get().getIdCartaoEmitido(), request);
            cartao.get().adicionarAvisoDeViagem(aviso, httpRequest);
            cartaoRepository.save(cartao.get());
        }//1
        catch (FeignException e){
            logger.error("Não foi possivel solicitar o aviso viagem do cartão id : {}", idCartao);
            return ResponseEntity.unprocessableEntity().build();
        }

        var ultimoAviso = avisoRepository.findFirstByOrderBySolicitadoEmDesc();
        logger.info("Solicitação de aviso viagem realizado com sucesso. Cartao id {}, Aviso id{}", idCartao, ultimoAviso.getId());

        return ResponseEntity.created(uri.path("/avisos/{id}")
                .buildAndExpand(ultimoAviso.getId())
                        .toUri()).build();
    }

}
