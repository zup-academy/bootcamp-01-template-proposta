package br.com.cartao.proposta.controller;

import br.com.cartao.proposta.domain.model.Cartao;
import br.com.cartao.proposta.domain.request.Bloqueio;
import br.com.cartao.proposta.domain.request.BloqueioRequest;
import br.com.cartao.proposta.domain.request.InformacaoRede;
import br.com.cartao.proposta.domain.response.BloqueioCartaoResponseDto;
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

@RestController
@RequestMapping("/v1/cartoes")
public class BloqueioCartaoController {

    private static Logger logger = LoggerFactory.getLogger(BloqueioCartaoController.class);

    @PersistenceContext
    private final EntityManager manager;

    public BloqueioCartaoController(EntityManager manager) {
        this.manager = manager;
    }

    @PostMapping("/{idCartao}/bloqueio")
    @Transactional
    // +1
    public ResponseEntity<?> bloqueioCartao(@PathVariable("idCartao") String idCartao,
                                            @RequestBody BloqueioRequest bloqueioRequest,
                                            UriComponentsBuilder uriComponentsBuilder,
                                            HttpServletRequest httpServletRequest){
        logger.info("Requisição para bloqueio de cartao com o id: {}", idCartao);
        // +1
        Optional<Cartao> cartao = Optional.ofNullable(manager.find(Cartao.class, idCartao));
        // +1
        if (cartao.isEmpty()){
            //Assert.notNull(cartao,"Não foi possivel achar o cartao com o id solicitado");
            return ResponseEntity.notFound().build();
        }
        // +1
        InformacaoRede informacaoRede = getInformacaoRede(httpServletRequest);
        // +1
        Bloqueio bloqueio = bloqueioRequest.toModel(cartao.get(), informacaoRede);

        BloqueioCartaoResponseDto bloqueioCartaoResponseDto = new BloqueioCartaoResponseDto(bloqueio);

        manager.persist(bloqueio);

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
