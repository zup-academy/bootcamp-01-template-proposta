package br.com.zup.bootcamp.proposta.domain.service;

import br.com.zup.bootcamp.proposta.api.externalsystem.LegadoSistemaCartao;
import br.com.zup.bootcamp.proposta.domain.repository.CartaoRepository;
import br.com.zup.bootcamp.proposta.domain.repository.PropostaRepository;
import br.com.zup.bootcamp.proposta.domain.service.enums.StatusProposta;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service @EnableScheduling
public class AssociaPropostaCartaoService {

    private final Logger logger = LoggerFactory.getLogger(AssociaPropostaCartaoService.class);
    @Autowired          //1
    private LegadoSistemaCartao sistemaCartao;
    @Autowired          //1
    private PropostaRepository propostaRepository;
    @Autowired          //1
    private CartaoRepository cartaoRepository;

    @Scheduled(cron = "0 0/1 * 1/1 * ?")
    public void associaCartaoGeradoComProposta(){
        logger.info("Varrer todas as propostas Elegivel e associar o Cartão a mesma");

        var propostasSemCartoes= propostaRepository.findByStatusAndCartaoNull(StatusProposta.ELEGIVEL);
                            //1
        propostasSemCartoes.forEach(proposta -> {
            //1
            try{
                var response = sistemaCartao.consultaCartaoPorIdProposta(proposta.getId());
                var cartao = response.toEntity();
                cartaoRepository.save(cartao);
                logger.info("cartao do titular: {} e id: {}  gerado com sucesso", cartao.getTitular(), cartao.getId());
                proposta.adicionarCartao(cartao);
                propostaRepository.save(proposta);
                logger.info("Cartão {} associado com sucesso a proposta {}", cartao.getId(), proposta.getId());
            }
            //1
            catch (FeignException e){
                logger.error("Falha ao associar cartao a proposta com ID: {}, Sistema de cartões: {} ", proposta.getId(),
                        e.getMessage());
            }
        });
    }
}
