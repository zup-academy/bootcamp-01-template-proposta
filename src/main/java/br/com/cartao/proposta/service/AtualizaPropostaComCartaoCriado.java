package br.com.cartao.proposta.service;


import br.com.cartao.proposta.domain.enums.EstadoProposta;
import br.com.cartao.proposta.domain.model.Cartao;
import br.com.cartao.proposta.domain.model.Proposta;
import br.com.cartao.proposta.repository.PropostaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AtualizaPropostaComCartaoCriado {

    private static Logger logger = LoggerFactory.getLogger(AtualizaPropostaComCartaoCriado.class);
    // +1
    private final EstadoProposta estadoProposta = EstadoProposta.ELEGIVEL;
    // +1
    private final VerificaCartaoCriadoService verificaCartaoCriadoService;
    // +1
    private final PropostaRepository propostaRepository;

    public AtualizaPropostaComCartaoCriado(VerificaCartaoCriadoService verificaCartaoCriadoService, PropostaRepository propostaRepository) {
        this.verificaCartaoCriadoService = verificaCartaoCriadoService;
        this.propostaRepository = propostaRepository;
    }

    @Scheduled(fixedDelayString = "${periodicidade.verifica-cartao-criado}")
    public void atualiza(){
        // +1
        List<Proposta> propostasAguardandoCartao = todasPropostasElegiveisSemCartao();
        logger.info("Quantidade de proposta sem cartão: {}", propostasAguardandoCartao.size());
        // +1
        if(propostasAguardandoCartao.size()> 0){
            percorreLista(propostasAguardandoCartao);
        }

    }

    private void percorreLista(List<Proposta> propostasAguardandoCartao) {

/*        propostasAguardandoCartao.stream()
                // +1
                .filter(proposta ->{
                    Optional<Cartao> cartao = verificaCartaoCriadoService.verificaSeCartaoCriado(proposta.getId());
                    logger.info("Cartão: {}", cartao);
                    return cartao.isPresent();
                })
                // +1
                .forEach(proposta ->
                    alteraStatusESalva(proposta, cartao.get())
                );*/

        propostasAguardandoCartao.forEach(proposta -> {
            Optional<Cartao> cartao = verificaCartaoCriadoService.verificaSeCartaoCriado(proposta.getId());
            logger.info("Cartão: {}", cartao);
            if (cartao.isPresent()){
                alteraStatusESalva(proposta, cartao.get());
            }
        });
    }

    private List<Proposta> todasPropostasElegiveisSemCartao(){
        logger.info("Selecionando todas propostas elegiveis sem cartao associado");
        return propostaRepository.findAllByCartaoCriadoFalseAndEstadoProposta(estadoProposta);
    }

    @Transactional
    private void alteraStatusESalva(Proposta proposta, Cartao cartao) {
        logger.info("Alterando status do cartão para o idProposta: {}", proposta.getId());
        proposta.alteraStatusCartaoCriado(Boolean.TRUE);
        proposta.adicionaNumeroCartao(cartao.getId());
        propostaRepository.save(proposta);
    }
}
