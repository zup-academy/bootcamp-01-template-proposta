package br.com.itau.cartaobrancoproposta.controller;

import br.com.itau.cartaobrancoproposta.component.TransacaoDados;
import br.com.itau.cartaobrancoproposta.model.Bloqueio;
import br.com.itau.cartaobrancoproposta.model.Cartao;
import br.com.itau.cartaobrancoproposta.model.EstadoBloqueio;
import br.com.itau.cartaobrancoproposta.service.BloqueioCartaoService;
import io.opentracing.Span;
import io.opentracing.Tracer;
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
public class CartaoController {

    private final Tracer tracer;

    private final Logger logger = LoggerFactory.getLogger(CartaoController.class);
//1
    private final TransacaoDados transacaoDados;
//1
    private final BloqueioCartaoService bloqueioCartaoService;

    public CartaoController(Tracer tracer, TransacaoDados transacaoDados, BloqueioCartaoService bloqueioCartaoService) {
        this.tracer = tracer;
        this.transacaoDados = transacaoDados;
        this.bloqueioCartaoService = bloqueioCartaoService;
    }

    @PostMapping("/v1/cartoes/{id}/bloqueio")
    public ResponseEntity<?> criaBloqueioCartao(@PathVariable("id") String numeroCartao, HttpServletRequest request, UriComponentsBuilder uriComponentsBuilder) {
        Cartao cartao = transacaoDados.buscaPorNumeroDoCartao(numeroCartao); //1

        if (cartao == null) { //1
            logger.error("Cartão não foi encontrado.");
            return ResponseEntity.notFound().build();
        }

        Span activeSpan = tracer.activeSpan();
        activeSpan.setTag("correlationID", cartao.getId());

        Bloqueio bloqueio = new Bloqueio(request.getRemoteAddr(), request.getHeader("User-Agent"), EstadoBloqueio.PENDENTE); //1 1

        transacaoDados.salva(bloqueio);
        logger.info("Bloqueio id={} estado={} foi criado com sucesso!", bloqueio.getId(), bloqueio.getEstadoBloqueio());

        cartao.carregaBloqueio(bloqueio);

        if (bloqueioCartaoService.notificaBloqueioDoCartaoNoLegado(cartao)) { //1
            bloqueio.statusBloqueado();
            transacaoDados.atualiza(cartao);
            logger.info("Bloqueio id={} estado={} foi atrelado ao cartão com final {} com sucesso!", bloqueio.getId(),
                    bloqueio.getEstadoBloqueio(), cartao.getNumeroCartao().substring(24));
        }

        URI uri = uriComponentsBuilder.path("/v1/cartoes/{id}").buildAndExpand(cartao.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
