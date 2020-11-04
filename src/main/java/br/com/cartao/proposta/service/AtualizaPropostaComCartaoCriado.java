package br.com.cartao.proposta.service;

import br.com.cartao.proposta.domain.enums.EstadoProposta;
import br.com.cartao.proposta.domain.model.Cartao;
import br.com.cartao.proposta.domain.response.CartaoResponseSistemaLegado;
import br.com.cartao.proposta.domain.model.Proposta;
import br.com.cartao.proposta.repository.CartaoRepository;
import br.com.cartao.proposta.repository.PropostaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    @Transactional
    protected void percorreLista(List<Proposta> propostasAguardandoCartao) {

        propostasAguardandoCartao.forEach(proposta -> {
            Optional<CartaoResponseSistemaLegado> cartao = verificaCartaoCriadoService.verificaSeCartaoCriado(proposta.getId());
            logger.info("Cartão: {}", cartao);
            if (cartao.isPresent()){
                alteraStatusESalva(proposta, cartao.get());
            }
        });
    }

    protected List<Proposta> todasPropostasElegiveisSemCartao(){
        logger.info("Selecionando todas propostas elegiveis sem cartao associado");
        return propostaRepository.findAllByCartaoCriadoFalseAndEstadoProposta(estadoProposta);
    }

    @Transactional
    protected void alteraStatusESalva(Proposta proposta, CartaoResponseSistemaLegado cartaoResponseSistemaLegado) {
        logger.info("Alterando status do cartão para o idProposta: {}", proposta.getId());
        proposta.alteraStatusCartaoCriado(Boolean.TRUE);
        // +1
        Cartao cartao = new Cartao(cartaoResponseSistemaLegado.getId(), proposta);
        proposta.adicionaNumeroCartao(cartao);
        propostaRepository.save(proposta);
    }
}
