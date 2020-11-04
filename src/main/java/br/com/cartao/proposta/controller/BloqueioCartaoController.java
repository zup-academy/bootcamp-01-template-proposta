package br.com.cartao.proposta.controller;

import br.com.cartao.proposta.domain.model.Cartao;
import br.com.cartao.proposta.domain.request.Bloqueio;
import br.com.cartao.proposta.domain.request.BloqueioRequest;
import br.com.cartao.proposta.domain.request.InformacaoRede;
import br.com.cartao.proposta.domain.response.BloqueioCartaoResponseDto;
import br.com.cartao.proposta.service.BloqueioCartaoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Carga intrínseca máxima permitida - 7
 * Carga intrínseca da classe - 7
 */

@RestController
@RequestMapping("/v1/cartoes")
public class BloqueioCartaoController {

    private static Logger logger = LoggerFactory.getLogger(BloqueioCartaoController.class);

    @PersistenceContext
    private final EntityManager manager;
    // +1
    private final BloqueioCartaoService bloqueioCartaoService;

    public BloqueioCartaoController(EntityManager manager, BloqueioCartaoService bloqueioCartaoService) {
        this.manager = manager;
        this.bloqueioCartaoService = bloqueioCartaoService;
    }

    @PostMapping("/{idCartao}/bloqueio")
    @Transactional
    // +1
    public ResponseEntity<?> bloqueioCartao(@PathVariable("idCartao") String idCartao,
                                            @RequestBody BloqueioRequest bloqueioRequest,
                                            UriComponentsBuilder uriComponentsBuilder,
                                            HttpServletRequest httpServletRequest) throws JsonProcessingException {
        logger.info("Requisição para bloqueio de cartao com o id: {}", idCartao);
        Optional<Cartao> cartao = Optional.ofNullable(manager.find(Cartao.class, idCartao));
        // +1
        if (cartao.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        // +1
        InformacaoRede informacaoRede = getInformacaoRede(httpServletRequest);
        // +1
        Bloqueio bloqueio = bloqueioRequest.toModel(cartao.get(), informacaoRede);

        bloqueioCartaoService.avisa(bloqueio,cartao.get());
        manager.persist(bloqueio);
        // +1
        BloqueioCartaoResponseDto bloqueioCartaoResponseDto = new BloqueioCartaoResponseDto(bloqueio);

        return ResponseEntity
                .created(uriComponentsBuilder.path("/carta/{idCartao}/bloqueio").buildAndExpand(bloqueioCartaoResponseDto.getId()).toUri())
                .build();
    }

    private InformacaoRede getInformacaoRede(HttpServletRequest httpServletRequest) {
        String userAgent = httpServletRequest.getHeader("User-Agent");
        String ipAddress = httpServletRequest.getHeader("X-FORWARDED-FOR");
        // +1
        if (StringUtils.isEmpty(ipAddress)) {
            ipAddress = httpServletRequest.getRemoteAddr();
        }

        InformacaoRede informacaoRede = new InformacaoRede(userAgent,ipAddress);
        return informacaoRede;
    }
}
