package br.com.itau.cartaobrancoproposta.controller;

import br.com.itau.cartaobrancoproposta.component.TransacaoDados;
import br.com.itau.cartaobrancoproposta.model.AvisoViagem;
import br.com.itau.cartaobrancoproposta.model.AvisoViagemRequest;
import br.com.itau.cartaobrancoproposta.model.Cartao;
import br.com.itau.cartaobrancoproposta.service.AvisoViagemService;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;

@RestController
public class AvisoViagemController {

    private final Tracer tracer;

    private final Logger logger = LoggerFactory.getLogger(AvisoViagemController.class);
//1
    private final TransacaoDados transacaoDados;
//1
    private final AvisoViagemService avisoViagemService;

    public AvisoViagemController(Tracer tracer, TransacaoDados transacaoDados, AvisoViagemService avisoViagemService) {
        this.tracer = tracer;
        this.transacaoDados = transacaoDados;
        this.avisoViagemService = avisoViagemService;
    }

    @PostMapping("/v1/cartoes/{id}/viagem")
    public ResponseEntity<?> criaAvisoViagem(@PathVariable("id") String numeroCartao, @Valid @RequestBody AvisoViagemRequest avisoViagemRequest, //1
                                          HttpServletRequest httpServletRequest, UriComponentsBuilder uriComponentsBuilder) {
        Cartao cartao = transacaoDados.buscaPorNumeroDoCartao(numeroCartao); //1

        if (cartao == null) { //1
            logger.error("Cartão não foi encontrado.");
            return ResponseEntity.notFound().build();
        }

        Span activeSpan = tracer.activeSpan();
        activeSpan.setTag("correlationID", cartao.getId());

        AvisoViagem avisoViagem = avisoViagemRequest.toModel(httpServletRequest.getRemoteAddr(), httpServletRequest.getHeader("User-Agent")); //1

        avisoViagemService.notificaAvisoViagem(numeroCartao, avisoViagem);

        transacaoDados.salva(avisoViagem);
        logger.info("Aviso viagem id={} destino={} validade={} criada com sucesso!", avisoViagem.getId(), avisoViagem.getDestino(), avisoViagem.getValidoAte());

        cartao.carregaAvisoViagem(avisoViagem);
        transacaoDados.atualiza(cartao);
        logger.info("Aviso viagem id={} destino={} validade={} foi atrelado ao cartão com final {} com sucesso!",
                avisoViagem.getId(), avisoViagem.getDestino(), avisoViagem.getValidoAte(), cartao.getNumeroCartao().substring(24));

        URI uri = uriComponentsBuilder.path("/v1/avisos/{id}").buildAndExpand(avisoViagem.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
