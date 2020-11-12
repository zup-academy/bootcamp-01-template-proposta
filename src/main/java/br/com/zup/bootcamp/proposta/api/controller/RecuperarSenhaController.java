package br.com.zup.bootcamp.proposta.api.controller;

import br.com.zup.bootcamp.proposta.domain.entity.RecuperarSenha;
import br.com.zup.bootcamp.proposta.domain.repository.CartaoRepository;
import br.com.zup.bootcamp.proposta.domain.repository.RecuperarSenhaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

@RestController
public class RecuperarSenhaController {

    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private RecuperarSenhaRepository senhaRepository;
    private final Logger logger = LoggerFactory.getLogger(RecuperarSenhaController.class);

    @PostMapping("/cartoes/{idCartao}/recuperarsenhas")
    public ResponseEntity<?> solicitarRecuperacaoDeSenha(@PathVariable String idCartao, HttpServletRequest httpRequest,
                                                         UriComponentsBuilder uri) {
        logger.info("Solicitação para Recuperar senha do cartao id: {} iniciada", idCartao);

        var cartao = cartaoRepository.findById(idCartao);
        if (cartao.isEmpty()) {
            logger.warn("Cartão com o id: {} não encontrado", idCartao);
            return ResponseEntity.notFound().build();
        }
        var solicitacao = new RecuperarSenha(httpRequest.getRemoteAddr(), httpRequest.getHeader("User-Agent"), cartao.get());
        senhaRepository.save(solicitacao);
        logger.info("Solicitação de recuperação de senha cadastrada com sucesso, Id: {}", solicitacao.getId());

        return ResponseEntity.created(uri.path("/recuperarsenha/{id}").
                buildAndExpand(solicitacao.getId()).toUri()).build();
    }
}
