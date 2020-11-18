package br.com.cartao.proposta.service;

import br.com.cartao.proposta.domain.dto.CarteiraDigitalDto;
import br.com.cartao.proposta.domain.enums.EstadoAssociacaoCarteiraIntegracao;
import br.com.cartao.proposta.domain.enums.EstadoAvisoLegado;
import br.com.cartao.proposta.domain.model.CarteiraDigital;
import br.com.cartao.proposta.domain.response.ResultadoAssociacaoCarteiraResponse;
import br.com.cartao.proposta.repository.CarteiraDigitalRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 *
 */

@Service
public class MudaStatusCarteiraDigital {

    // +1
    private static EstadoAvisoLegado estadoAvisoLegado = EstadoAvisoLegado.AVISADO;
    // +1
    private final CarteiraDigitalRepository carteiraDigitalRepository;

    public MudaStatusCarteiraDigital(CarteiraDigitalRepository carteiraDigitalRepository) {
        this.carteiraDigitalRepository = carteiraDigitalRepository;
    }
    // +2
    public String muda(CarteiraDigitalDto carteiraDigitalDto, Optional<ResultadoAssociacaoCarteiraResponse> resultadoAssociacaoCarteiraResponse){
        // +1
        CarteiraDigital carteiraDigital = carteiraDigitalRepository.findByCartaoIdAndCarteiraAndEstadoAvisoLegadoNotLike(carteiraDigitalDto.getCartaoId(), carteiraDigitalDto.getCarteira(), estadoAvisoLegado)
                .orElse(carteiraDigitalDto.toModel());

        // +1
        if (EstadoAssociacaoCarteiraIntegracao.FALHA.equals(resultadoAssociacaoCarteiraResponse.get().getResultado()) || resultadoAssociacaoCarteiraResponse.isEmpty()){
            carteiraDigital.associacaoFalhaCarteira();
        }
        // +1
        else if (EstadoAssociacaoCarteiraIntegracao.ASSOCIADA.equals(resultadoAssociacaoCarteiraResponse.get().getResultado())){
            carteiraDigital.associacaoSucessoCarteira();
        }

        CarteiraDigital carteiraDigitalSalva = carteiraDigitalRepository.save(carteiraDigital);

        return carteiraDigitalSalva.getId();
    }
}
