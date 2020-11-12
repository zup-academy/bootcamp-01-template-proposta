package br.com.zup.bootcamp.proposta.domain.service;

import br.com.zup.bootcamp.proposta.api.externalsystem.LegadoSistemaCartao;
import br.com.zup.bootcamp.proposta.domain.entity.Cartao;
import br.com.zup.bootcamp.proposta.domain.service.enums.EstadoCartao;
import br.com.zup.bootcamp.proposta.domain.repository.BloqueioRepository;
import br.com.zup.bootcamp.proposta.domain.repository.CartaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class BloqueiaCartaoService {

    @Autowired              //1
    private LegadoSistemaCartao sistemaCartao;
    @Autowired              //1
    private CartaoRepository cartaoRepository;
    @Autowired              //1
    private BloqueioRepository bloqueioRepository;

    private final Logger logger = LoggerFactory.getLogger(BloqueiaCartaoService.class);
                                                                                //1
    public ResponseEntity<?> processaBloqueio(HttpServletRequest httpRequest, Cartao cartao, UriComponentsBuilder uri) {
        logger.info("Processando bloqueio do cartao com o id: {}, IP da requisão: {}, user-agent: {}",
                    cartao.getId(), httpRequest.getRemoteAddr(), httpRequest.getHeader("User-Agent"));
        //1
        if (cartao.getEstadoCartao().equals(EstadoCartao.BLOQUEADO)) {
            logger.warn("Cartão com o id {} ja esta bloqueado", cartao.getId());
            return ResponseEntity.unprocessableEntity().build();
        }

        var responseBloqueio = sistemaCartao.bloqueiaCartaoPorIdCartao(cartao.getIdCartaoEmitido(),
                Map.of("sistemaResponsavel", httpRequest.getHeader("User-Agent")));
        logger.info("Request realizada no sistema legado retornou o status code {}", responseBloqueio.getStatusCodeValue());
        //1
        if (!responseBloqueio.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.badRequest().build();
        }

        cartao.adicionarBloqueioDoCartao(httpRequest.getRemoteAddr(), httpRequest.getHeader("User-Agent"));

        cartaoRepository.save(cartao);

        var ultimoBloqueio = bloqueioRepository.findFirstByOrderByBloqueadoEmDesc();

        logger.info("Bloqueio do cartão do id {} realizado com sucesso", cartao.getId());
        return ResponseEntity.created(uri.path("/cartoes/bloqueios/{id}").
                buildAndExpand(ultimoBloqueio.getId()).toUri()).build();
    }
}
