package br.com.cartao.proposta.service;

import br.com.cartao.proposta.domain.dto.CarteiraDigitalDto;
import br.com.cartao.proposta.domain.enums.EstadoAssociacaoCarteiraIntegracao;
import br.com.cartao.proposta.domain.model.CarteiraDigital;
import br.com.cartao.proposta.domain.response.ResultadoAssociacaoCarteiraResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Carga intrínseca máxima permitida - 7
 * Carga intrínseca da classe - 7
 */

@Service
// +1
public class CarteiraDigitalServiceImpl implements AssociacaoCarteiraCartao {

    @PersistenceContext
    private final EntityManager manager;
    // +1
    private final CarteiraDigitalIntegracaoService carteiraDigitalIntegracaoService;

    public CarteiraDigitalServiceImpl(EntityManager manager, CarteiraDigitalIntegracaoService carteiraDigitalIntegracaoService) {
        this.manager = manager;
        this.carteiraDigitalIntegracaoService = carteiraDigitalIntegracaoService;
    }

    @Override
    @Transactional
    // +2
    public CarteiraDigital associa(CarteiraDigitalDto carteiraDigitalDto) throws JsonProcessingException {
        // +1
        Optional<ResultadoAssociacaoCarteiraResponse> resultadoAssociacaoCarteiraResponse = carteiraDigitalIntegracaoService.associar(carteiraDigitalDto);

        CarteiraDigital carteiraDigital = carteiraDigitalDto.toModel();
        // +1
        if (EstadoAssociacaoCarteiraIntegracao.FALHA.equals(resultadoAssociacaoCarteiraResponse.get().getResultado()) || resultadoAssociacaoCarteiraResponse.isEmpty()){
            carteiraDigital.associacaoFalhaCarteira();
        }
        // +1
        else if (EstadoAssociacaoCarteiraIntegracao.ASSOCIADA.equals(resultadoAssociacaoCarteiraResponse.get().getResultado())){
            carteiraDigital.associacaoSucessoCarteira();
        }

        manager.persist(carteiraDigital);

        return carteiraDigital;

    }

}
