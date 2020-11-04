package br.com.cartao.proposta.service;

import br.com.cartao.proposta.domain.enums.EstadoBloqueioCartaoIntegracao;
import br.com.cartao.proposta.domain.enums.EstadoCartao;
import br.com.cartao.proposta.domain.enums.SolicitacaoBloqueioIntegracaoResponse;
import br.com.cartao.proposta.domain.model.Cartao;
import br.com.cartao.proposta.domain.request.Bloqueio;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Carga intrínseca máxima permitida - 7
 * Carga intrínseca da classe - 7
 */

@Service
public class BloqueioCartaoService {

    private final EntityManager manager;
    // +1
    private final AvisoBloqueioIntegracaoService avisoBloqueioIntegracaoService;


    public BloqueioCartaoService(EntityManager manager, AvisoBloqueioIntegracaoService avisoBloqueioIntegracaoService) {
        this.manager = manager;
        this.avisoBloqueioIntegracaoService = avisoBloqueioIntegracaoService;
    }

    @Transactional
    // +2
    public void avisa(Bloqueio bloqueio, Cartao cartao) throws JsonProcessingException {

        Cartao cartaoBuscado = manager.find(Cartao.class, cartao.getId());
        // +1
        Optional<SolicitacaoBloqueioIntegracaoResponse> solicitacaoBloqueioIntegracaoResponse = avisoBloqueioIntegracaoService.avisaSistema(cartao);

        // +1
        if (isCartaoAtivoEIntegracaoFalhaOUVazia(cartaoBuscado, solicitacaoBloqueioIntegracaoResponse)){
            cartaoBuscado.estadoCartaoComFalha();
        }
        // +1
        else if (isCartaoAtivoEIntegracaoSucesso(cartaoBuscado, solicitacaoBloqueioIntegracaoResponse)){
            cartaoBuscado.estadoCartaoBloqueado();
        }
        // +1
        else if (isCartaoFalhaEIntegracaoSucesso(cartaoBuscado, solicitacaoBloqueioIntegracaoResponse)){
            cartaoBuscado.estadoCartaoBloqueado();
        }

        manager.merge(cartaoBuscado);

    }

    private boolean isCartaoAtivoEIntegracaoFalhaOUVazia(Cartao cartaoBuscado, Optional<SolicitacaoBloqueioIntegracaoResponse> solicitacaoBloqueioIntegracaoResponse) {
        return EstadoCartao.ATIVO.equals(cartaoBuscado.getEstadoCartao())
                && (solicitacaoBloqueioIntegracaoResponse.isEmpty()
                || EstadoBloqueioCartaoIntegracao.FALHA.equals(solicitacaoBloqueioIntegracaoResponse.get().getResultado()));
    }

    private boolean isCartaoAtivoEIntegracaoSucesso(Cartao cartaoBuscado, Optional<SolicitacaoBloqueioIntegracaoResponse> solicitacaoBloqueioIntegracaoResponse) {
        return EstadoCartao.ATIVO.equals(cartaoBuscado.getEstadoCartao())
                && EstadoBloqueioCartaoIntegracao.BLOQUEADO.equals(solicitacaoBloqueioIntegracaoResponse.get().getResultado());
    }

    private boolean isCartaoFalhaEIntegracaoSucesso(Cartao cartaoBuscado, Optional<SolicitacaoBloqueioIntegracaoResponse> solicitacaoBloqueioIntegracaoResponse) {
        return EstadoCartao.FALHA.equals(cartaoBuscado.getEstadoCartao()) && EstadoBloqueioCartaoIntegracao.BLOQUEADO.equals(solicitacaoBloqueioIntegracaoResponse.get().getResultado());
    }
}
