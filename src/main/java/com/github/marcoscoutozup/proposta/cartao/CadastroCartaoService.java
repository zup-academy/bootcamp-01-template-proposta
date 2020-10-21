package com.github.marcoscoutozup.proposta.cartao;

import com.github.marcoscoutozup.proposta.proposta.Proposta;
import com.github.marcoscoutozup.proposta.proposta.PropostaRepository;
import com.github.marcoscoutozup.proposta.proposta.enums.StatusDaProposta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class CadastroCartaoService {

    private CartaoClient cartaoClient;
    private PropostaRepository propostaRepository;
    private CartaoRepository cartaoRepository;
    private Logger logger;

                                    //1                             //2                                 //3
    public CadastroCartaoService(CartaoClient cartaoClient, PropostaRepository propostaRepository, CartaoRepository cartaoRepository) {
        this.cartaoClient = cartaoClient;
        this.propostaRepository = propostaRepository;
        this.cartaoRepository = cartaoRepository;
        this.logger = LoggerFactory.getLogger(CadastroCartaoService.class);
    }

    @Scheduled(fixedDelayString = "${tempo.verificadordecartao}")
    public void verificarSeExisteCartaoCadastradoNaProposta(){
        logger.info("[SCHEDULED] Verificar se existe cartão cadastrado na proposta");

                //4
        List<Proposta> propostas = propostaRepository.findByStatusDaProposta(StatusDaProposta.ELEGIVEL);
                            //5
        propostas.forEach(proposta -> {
            Cartao cartao = cartaoClient.pesquisarCartaoPorIdDaProposta(proposta.getId().toString());
            //6
            if(proposta.verificarSeNaoExisteCartao()){
                Assert.notNull(cartao, "O cartão não pode ser nulo");
                cartaoRepository.save(cartao);
                proposta.incluirCartaoNaProposta(cartao);
                propostaRepository.save(proposta);
                logger.info("[INCLUSÃO DE CARTÃO NA PROPOSTA] Cartão incluso na proposta " + proposta.toString());
            }
        });
    }

}
