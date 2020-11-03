package br.com.zup.cartaoproposta.services.cartao;

import br.com.zup.cartaoproposta.clienteswebservices.CartoesClient;
import br.com.zup.cartaoproposta.entities.cartao.Cartao;
import br.com.zup.cartaoproposta.entities.cartao.DadosCartaoRetorno;
import br.com.zup.cartaoproposta.entities.proposta.Proposta;
import br.com.zup.cartaoproposta.entities.proposta.StatusProposta;
import br.com.zup.cartaoproposta.repositories.CartaoRepository;
import br.com.zup.cartaoproposta.repositories.PropostaRepository;
import feign.FeignException;
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

    @Scheduled(fixedDelayString = "${periodicidade.criar-cartao}")
    @Transactional
    public void criacaoDoCartao(){

        //2
        List<Proposta> propostasValidas = propostaRepository.findByStatusProposta(StatusProposta.AGUARDANDO_CARTAO);
        //1
        propostasValidas.forEach( p -> {
            //2
            try {
                //1
                DadosCartaoRetorno dadosCartao = cartoesClient.buscaDadosCartoesResource(p.getId());
                //1
                Cartao cartao = dadosCartao.toModel(propostaRepository);
                cartaoRepository.save(cartao);
                p.atualizaStatusProposta();
            } catch (FeignException ignored) {}

        });

    }
}
