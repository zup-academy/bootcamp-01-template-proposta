package br.com.cartao.proposta.service;

import br.com.cartao.proposta.domain.enums.EstadoProposta;
import br.com.cartao.proposta.domain.event.DadosCartaoProposta;
import br.com.cartao.proposta.domain.model.Cartao;
import br.com.cartao.proposta.domain.response.CartaoResponseSistemaLegado;
import br.com.cartao.proposta.domain.model.Proposta;
import br.com.cartao.proposta.repository.PropostaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Carga intrínseca máxima permitida - 7
 * Carga intrínseca da classe - 11
 */

@Service
public class AtualizaPropostaComCartaoCriado {

    private static Logger logger = LoggerFactory.getLogger(AtualizaPropostaComCartaoCriado.class);
    // +1
    private final EstadoProposta estadoProposta = EstadoProposta.ELEGIVEL;
    // +1
    private final PropostaKafkaProducer propostaKafkaProducer;
    // +1
    private final VerificaCartaoCriadoIntegracaoService verificaCartaoCriadoService;
    // +1
    private final PropostaRepository propostaRepository;

    public AtualizaPropostaComCartaoCriado(PropostaKafkaProducer propostaKafkaProducer, VerificaCartaoCriadoIntegracaoService verificaCartaoCriadoService, PropostaRepository propostaRepository) {
        this.propostaKafkaProducer = propostaKafkaProducer;
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
        // +1
        propostasAguardandoCartao.forEach(proposta -> {
            Optional<CartaoResponseSistemaLegado> cartao = verificaCartaoCriadoService.verificaSeCartaoCriado(proposta.getId());
            logger.info("Cartão: {}", cartao);
            // +1
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
    // +1
    protected void alteraStatusESalva(Proposta proposta, CartaoResponseSistemaLegado cartaoResponseSistemaLegado) {
        logger.info("Alterando status do cartão para o idProposta: {}", proposta.getId());
        proposta.alteraStatusCartaoCriado(Boolean.TRUE);
        // +1
        Cartao cartao = new Cartao(cartaoResponseSistemaLegado.getCartaoId(), proposta);

        proposta.adicionaNumeroCartao(cartao);
        Proposta propostaAtualizadaComCartao = propostaRepository.save(proposta);
        // +1
        DadosCartaoProposta dadosCartaoProposta = new DadosCartaoProposta(propostaAtualizadaComCartao);
        propostaKafkaProducer.notifica(dadosCartaoProposta);
    }
}
