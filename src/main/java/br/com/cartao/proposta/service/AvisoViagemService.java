package br.com.cartao.proposta.service;

import br.com.cartao.proposta.domain.enums.EstadoAvisoViagemIntegracao;
import br.com.cartao.proposta.domain.model.AvisoViagem;
import br.com.cartao.proposta.domain.model.Cartao;
import br.com.cartao.proposta.domain.response.ResultadoAvisoViagemIntegracao;
import br.com.cartao.proposta.repository.AvisoViagemRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Carga intrínseca máxima permitida - 7
 * Carga intrínseca da classe - 8
 */

@Service
public class AvisoViagemService {
    // +1
    private final AvisoViagemRepository avisoViagemRepository;
    // +1
    private final AvisoViagemIntegracaoService avisoViagemIntegracaoService;

    public AvisoViagemService(AvisoViagemRepository avisoViagemRepository, AvisoViagemIntegracaoService avisoViagemIntegracaoService) {
        this.avisoViagemRepository = avisoViagemRepository;
        this.avisoViagemIntegracaoService = avisoViagemIntegracaoService;
    }

    // +2
    @Transactional
    public void avisoViagem(Cartao cartao, AvisoViagem avisoViagem) throws JsonProcessingException {
        // +1
        AvisoViagem avisoViagemBuscada = avisoViagemRepository.findByCartaoNumeroCartaoAndDestinoViagem( cartao.getNumeroCartao(), avisoViagem.getDestinoViagem())
                .orElse(avisoViagem);

        // +1
        Optional<ResultadoAvisoViagemIntegracao> resultadoAvisoViagemIntegracao = avisoViagemIntegracaoService.avisaViagem(cartao, avisoViagem);
        // +1
        if ((avisoViagemBuscada.getAvisoSistemaLegado() == Boolean.FALSE) && (resultadoAvisoViagemIntegracao.isEmpty() || EstadoAvisoViagemIntegracao.FALHA.equals(resultadoAvisoViagemIntegracao.get().getResultado()))){
            avisoViagemBuscada.estadoAvisoComFalha();
        }
        // +1
        else if ( (avisoViagemBuscada.getAvisoSistemaLegado() == Boolean.FALSE) && EstadoAvisoViagemIntegracao.CRIADO.equals(resultadoAvisoViagemIntegracao.get().getResultado())){
            avisoViagemBuscada.estadoAvisoComSucesso();
        }

        avisoViagemRepository.save(avisoViagemBuscada);
    }

}
