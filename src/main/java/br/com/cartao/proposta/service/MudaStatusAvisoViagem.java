package br.com.cartao.proposta.service;

import br.com.cartao.proposta.domain.enums.EstadoAvisoViagemIntegracao;
import br.com.cartao.proposta.domain.model.AlteraStatusAvisoViagem;
import br.com.cartao.proposta.domain.model.AvisoViagem;
import br.com.cartao.proposta.domain.model.Cartao;
import br.com.cartao.proposta.domain.response.ResultadoAvisoViagemIntegracao;
import br.com.cartao.proposta.repository.AvisoViagemRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Carga intrínseca máxima permitida - 7
 * Carga intrínseca da classe - 7
 */

@Service
public class MudaStatusAvisoViagem {

    // +1
    private final AvisoViagemRepository avisoViagemRepository;

    public MudaStatusAvisoViagem(AvisoViagemRepository avisoViagemRepository) {
        this.avisoViagemRepository = avisoViagemRepository;
    }
    // +1
    public void mudaEstado(AlteraStatusAvisoViagem alteraStatusAvisoViagem){
        // +1
        AvisoViagem avisoViagem = alteraStatusAvisoViagem.getAvisoViagem();
        // +1
        Optional<ResultadoAvisoViagemIntegracao> resultadoAvisoViagemIntegracao = alteraStatusAvisoViagem.getResultadoAvisoViagemIntegracao();
        // +1
        Cartao cartao = alteraStatusAvisoViagem.getCartao();

        AvisoViagem avisoViagemBuscada = avisoViagemRepository.findByCartaoNumeroCartaoAndDestinoViagem( cartao.getNumeroCartao(), avisoViagem.getDestinoViagem())
                .orElse(avisoViagem);
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
