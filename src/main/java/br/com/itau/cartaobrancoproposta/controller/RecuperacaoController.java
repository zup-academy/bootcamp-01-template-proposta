package br.com.itau.cartaobrancoproposta.controller;

import br.com.itau.cartaobrancoproposta.component.TransacaoDados;
import br.com.itau.cartaobrancoproposta.model.Cartao;
import br.com.itau.cartaobrancoproposta.model.Recuperacao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@RestController
public class RecuperacaoController {

    private final Logger logger = LoggerFactory.getLogger(RecuperacaoController.class);
//1
    private final TransacaoDados transacaoDados;

    public RecuperacaoController(TransacaoDados transacaoDados) {
        this.transacaoDados = transacaoDados;
    }

    @PostMapping("/v1/cartoes/{id}/recuperacao")
    public ResponseEntity<?> recuperaSenha(@PathVariable("id") String numeroCartao, HttpServletRequest httpServletRequest, UriComponentsBuilder uriComponentsBuilder) {
        Cartao cartao = transacaoDados.buscaPorNumeroDoCartao(numeroCartao);

        if (cartao == null) { //1
            logger.error("Cartão não foi encontrado.");
            return ResponseEntity.notFound().build();
        }

        Recuperacao recuperacao = new Recuperacao(httpServletRequest.getRemoteAddr(), httpServletRequest.getHeader("User-Agent")); //1

        cartao.carregaRecuperacaoSenha(recuperacao);
        transacaoDados.atualiza(cartao);

        logger.info("Recuperação de senha do cartão com final {} foi solicitado com sucesso!", cartao.getNumeroCartao().substring(24));

        URI uri = uriComponentsBuilder.path("/v1/recuperacao/{id}").buildAndExpand(recuperacao.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
