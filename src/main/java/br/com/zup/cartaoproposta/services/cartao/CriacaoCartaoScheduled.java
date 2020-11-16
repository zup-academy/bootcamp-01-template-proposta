package br.com.zup.cartaoproposta.services.cartao;

import br.com.zup.cartaoproposta.entities.proposta.Proposta;
import br.com.zup.cartaoproposta.entities.proposta.StatusProposta;
import br.com.zup.cartaoproposta.repositories.PropostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Contagem de carga intrínseca da classe: 5
 */

@Component
public class CriacaoCartaoScheduled {

    @Autowired
    //1
    private PropostaRepository propostaRepository;

    @Autowired
    //1
    private CriarUmCartao criacaoUmCartao;

    @Scheduled(fixedDelayString = "${periodicidade.criar-cartao}")
    public void criacaoDoCartao(){

        //2
        List<Proposta> propostasValidas = propostaRepository.findByStatusProposta(StatusProposta.AGUARDANDO_CARTAO);

        //1
        propostasValidas.forEach(criacaoUmCartao::criacaoUmCartao);
    }


}
