package br.com.zup.cartaoproposta.services.cartao;

import br.com.zup.cartaoproposta.clienteswebservices.CartoesClient;
import br.com.zup.cartaoproposta.entities.cartao.Cartao;
import br.com.zup.cartaoproposta.entities.cartao.DadosCartaoRetornoLegado;
import br.com.zup.cartaoproposta.entities.proposta.Proposta;
import br.com.zup.cartaoproposta.repositories.PropostaRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * Contagem de carga intrínseca da classe: 7
 */

@Component
public class CriarUmCartao {

    @Autowired
    //1
    private CartoesClient cartoesClient;

    @Autowired
    //1
    private PropostaRepository propostaRepository;

    private final Logger logger = LoggerFactory.getLogger(CriacaoCartaoScheduled.class);

    @Transactional
    public void criacaoUmCartao(Proposta p) {
        //2
        try {
            logger.info("Busca dos dados do cartão da proposta. ipProposta: {}; documentoProposta: {}",p.getId(), p.getDocumento());
            //1
            DadosCartaoRetornoLegado dadosCartao = cartoesClient.dadosCartaoPelaPropostaResource(p.getId());
            //1
            Cartao cartao = dadosCartao.toModel(propostaRepository);
            //1
            if (p.getCartao() == null) {
                p.setCartao(cartao);
                p.atualizaStatusProposta();
                propostaRepository.save(p);
            }
        } catch (FeignException e) {
            logger.warn("Não localizado os dados do cartão da proposta. ipProposta: {}; documentoProposta: {}; statusRetorno: {}",p.getId(), p.getDocumento(), e.status());
        }
    }
}
