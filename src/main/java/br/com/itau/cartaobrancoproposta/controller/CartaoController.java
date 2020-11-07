package br.com.itau.cartaobrancoproposta.controller;

import br.com.itau.cartaobrancoproposta.component.TransacaoDados;
import br.com.itau.cartaobrancoproposta.model.Bloqueio;
import br.com.itau.cartaobrancoproposta.model.Cartao;
import br.com.itau.cartaobrancoproposta.model.EstadoBloqueio;
import br.com.itau.cartaobrancoproposta.service.BloqueioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CartaoController {

    private final Logger logger = LoggerFactory.getLogger(CartaoController.class);
//1
    private final TransacaoDados transacaoDados;
//1
    private final BloqueioService bloqueioService;

    public CartaoController(TransacaoDados transacaoDados, BloqueioService bloqueioService) {
        this.transacaoDados = transacaoDados;
        this.bloqueioService = bloqueioService;
    }

    @PostMapping("/v1/cartoes/{id}/bloqueio")
    public ResponseEntity<?> bloqueiaCartao(@PathVariable("id") String numeroCartao, HttpServletRequest request) {
        Cartao cartao = transacaoDados.buscaPorNumeroDoCartao(numeroCartao); //1

        if (cartao == null) { //1
            logger.error("Cartão não foi encontrado.");
            return ResponseEntity.notFound().build();
        }

        Bloqueio bloqueio = new Bloqueio(request.getRemoteAddr(), request.getHeader("User-Agent"), EstadoBloqueio.PENDENTE); //1 1

        transacaoDados.salva(bloqueio);
        logger.info("Bloqueio id={} estado={} foi criado com sucesso!", bloqueio.getId(), bloqueio.getEstadoBloqueio());

        cartao.carregaBloqueio(bloqueio);

        if (bloqueioService.notificaBloqueioDoCartaoNoLegado(cartao)) { //1
            bloqueio.statusBloqueado();
            transacaoDados.atualiza(cartao);
            logger.info("Bloqueio id={} estado={} foi atrelado ao cartão com final {} com sucesso!", bloqueio.getId(),
                    bloqueio.getEstadoBloqueio(), cartao.getNumeroCartao().substring(24));
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
