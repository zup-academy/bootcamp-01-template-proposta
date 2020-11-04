package br.com.zup.cartaoproposta.services.cartao;

import br.com.zup.cartaoproposta.clienteswebservices.CartoesClient;
import br.com.zup.cartaoproposta.entities.cartao.Cartao;
import br.com.zup.cartaoproposta.entities.cartao.DadosCartaoRetornoLegado;
import br.com.zup.cartaoproposta.entities.proposta.Proposta;
import br.com.zup.cartaoproposta.entities.proposta.StatusProposta;
import br.com.zup.cartaoproposta.repositories.CartaoRepository;
import br.com.zup.cartaoproposta.repositories.PropostaRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Contagem de carga intrínseca da classe: 9
 */

@Component
public class CriacaoCartao {

    @Autowired
    //1
    private CartoesClient cartoesClient;

    @Autowired
    //1
    private PropostaRepository propostaRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    private final Logger logger = LoggerFactory.getLogger(CriacaoCartao.class);

    @Scheduled(fixedDelayString = "${periodicidade.criar-cartao}")
    @Transactional
    public void criacaoDoCartao(){

        //2
        List<Proposta> propostasValidas = propostaRepository.findByStatusProposta(StatusProposta.AGUARDANDO_CARTAO);
        //1
        propostasValidas.forEach( p -> {
            //2
            try {
                logger.info("Busca dos dados do cartão da proposta. ipProposta: {}; documentoProposta: {}",p.getId(), p.getDocumento());
                //1
                DadosCartaoRetornoLegado dadosCartao = cartoesClient.buscaDadosCartoesResource(p.getId());
                //1
                Cartao cartao = dadosCartao.toModel(propostaRepository);
                cartaoRepository.save(cartao);
                p.atualizaStatusProposta();
            } catch (FeignException e) {
                logger.warn("Não localizado os dados do cartão da proposta. ipProposta: {}; documentoProposta: {}; statusRetorno: {}",p.getId(), p.getDocumento(), e.status());
            }

        });

    }
}
