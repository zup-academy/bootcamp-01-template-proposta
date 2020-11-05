package br.com.cartao.proposta.controller;

import br.com.cartao.proposta.domain.model.AvisoViagem;
import br.com.cartao.proposta.domain.model.Cartao;
import br.com.cartao.proposta.domain.request.AvisoViagemRequest;
import br.com.cartao.proposta.domain.request.InformacaoRede;
import br.com.cartao.proposta.domain.response.AvisoViagemResponseDto;
import br.com.cartao.proposta.repository.AvisoViagemRepository;
import br.com.cartao.proposta.repository.CartaoRepository;
import br.com.cartao.proposta.service.AvisoViagemService;
import br.com.cartao.proposta.service.ColetaInformacaoRedeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

/**
 * Carga intrínseca máxima permitida - 7
 * Carga intrínseca da classe - 8
 */

@RestController
@RequestMapping("/v1/cartoes")
public class AvisoViagemController {

    private static Logger logger = LoggerFactory.getLogger(AvisoViagemController.class);

    @PersistenceContext
    private final EntityManager manager;
    // +1
    private final ColetaInformacaoRedeService coletaInformacaoRedeService;
    // +1
    private final AvisoViagemService avisoViagemService;

    public AvisoViagemController(EntityManager manager, ColetaInformacaoRedeService coletaInformacaoRedeService, AvisoViagemService avisoViagemService) {
        this.manager = manager;
        this.coletaInformacaoRedeService = coletaInformacaoRedeService;
        this.avisoViagemService = avisoViagemService;
    }

    @PostMapping("/{id}/viagens")
    @Transactional
    // +1
    public ResponseEntity<?> avisoViagem(@PathVariable(value = "id",required = true) String idCartao,
                                         @RequestBody @Valid AvisoViagemRequest avisoViagemRequest,
                                         HttpServletRequest httpServletRequest,
                                         UriComponentsBuilder uriComponentsBuilder) throws JsonProcessingException {
        logger.info("Requisição para avisar Viagem recebida de cartão id: {}. Requisição: {}",idCartao, avisoViagemRequest);
        // +1
        Cartao cartao = manager.find(Cartao.class, idCartao);
        // +1
        if (cartao == null){
            logger.warn("Não existe cartão para o id: {} ", idCartao);
            return ResponseEntity.notFound().build();
        }
        // +1
        InformacaoRede informacaoRede = coletaInformacaoRedeService.getInformacaoRede(httpServletRequest);
        // +1
        AvisoViagem avisoViagem = avisoViagemRequest.toModel(cartao, informacaoRede);

        avisoViagemService.avisoViagem(cartao,avisoViagem);

        // +1
        AvisoViagemResponseDto avisoViagemResponseDto = new AvisoViagemResponseDto(avisoViagem);

        return ResponseEntity
                .created(uriComponentsBuilder.path("/v1/cartoes/{idCartao}/viagens/{id}").buildAndExpand(idCartao,avisoViagem.getId()).toUri())
                .build();
    }
}
