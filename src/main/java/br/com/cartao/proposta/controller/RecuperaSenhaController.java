package br.com.cartao.proposta.controller;

import br.com.cartao.proposta.domain.model.Cartao;
import br.com.cartao.proposta.domain.model.RecuperaSenha;
import br.com.cartao.proposta.domain.request.InformacaoRede;
import br.com.cartao.proposta.domain.response.RecuperaSenhaResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@RestController
@RequestMapping("/v1/cartoes")
public class RecuperaSenhaController {

    private static Logger logger = LoggerFactory.getLogger(RecuperaSenhaController.class);

    @PersistenceContext
    private final EntityManager manager;

    public RecuperaSenhaController(EntityManager manager) {
        this.manager = manager;
    }

    @PostMapping("/{id}/recupera-senha")
    @Transactional
    public ResponseEntity<?> recuperaSenha(@PathVariable(value = "id", required = true) String idCartao,
                                           HttpServletRequest httpServletRequest,
                                           UriComponentsBuilder uriComponentsBuilder){
        logger.info("Requisição para recuperar senha recebida. idCartao: {}", idCartao);
        // +1
        Cartao cartao = manager.find(Cartao.class, idCartao);
        // +1
        if (cartao == null){
            logger.warn("Não existe cartão para o id: {} ", idCartao);
            return ResponseEntity.notFound().build();
        }
        // +1
        InformacaoRede informacaoRede = getInformacaoRede(httpServletRequest);
        // +1
        RecuperaSenha recuperaSenha = new RecuperaSenha(informacaoRede,cartao);

        manager.persist(recuperaSenha);
        // +1
        RecuperaSenhaResponseDto recuperaSenhaResponseDto = new RecuperaSenhaResponseDto(recuperaSenha);

        logger.info("Recuperação de senha solicitada com sucesso. Requisição: {}", recuperaSenhaResponseDto);
        return ResponseEntity
                .created(uriComponentsBuilder.path("/v1/cartoes/{idCartao}/recupera-senha/{id}").buildAndExpand(idCartao,recuperaSenhaResponseDto.getId()).toUri())
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
