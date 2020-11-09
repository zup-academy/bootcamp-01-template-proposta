package br.com.cartao.proposta.service;

import br.com.cartao.proposta.domain.dto.CarteiraDigitalDto;
import br.com.cartao.proposta.domain.enums.EstadoAssociacaoCarteiraIntegracao;
import br.com.cartao.proposta.domain.enums.EstadoAvisoLegado;
import br.com.cartao.proposta.domain.model.CarteiraDigital;
import br.com.cartao.proposta.domain.response.ResultadoAssociacaoCarteiraResponse;
import br.com.cartao.proposta.repository.CarteiraDigitalRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Carga intrínseca máxima permitida - 7
 * Carga intrínseca da classe - 9
 */

@Service
// +1
public class CarteiraDigitalServiceImpl implements AssociacaoCarteiraCartao {

    private static Logger logger = LoggerFactory.getLogger(CarteiraDigitalServiceImpl.class);

    private static EstadoAvisoLegado estadoAvisoLegado = EstadoAvisoLegado.AVISADO;

    // +1
    private final CarteiraDigitalRepository carteiraDigitalRepository;
    // +1
    private final CarteiraDigitalIntegracaoService carteiraDigitalIntegracaoService;

    public CarteiraDigitalServiceImpl(CarteiraDigitalRepository carteiraDigitalRepository, CarteiraDigitalIntegracaoService carteiraDigitalIntegracaoService) {
        this.carteiraDigitalRepository = carteiraDigitalRepository;
        this.carteiraDigitalIntegracaoService = carteiraDigitalIntegracaoService;
    }

    @Override
    @Transactional
    // +2
    public CarteiraDigital associa(CarteiraDigitalDto carteiraDigitalDto) throws JsonProcessingException {
        logger.info("Inicio do serviço responsavel por salvar a associação de carteira digital!");

        // +1
        Optional<ResultadoAssociacaoCarteiraResponse> resultadoAssociacaoCarteiraResponse = carteiraDigitalIntegracaoService.associar(carteiraDigitalDto);

        // +1
        CarteiraDigital carteiraDigital = carteiraDigitalRepository.findByCartaoIdAndCarteiraAndEstadoAvisoLegadoNotLike(carteiraDigitalDto.getCartaoId(), carteiraDigitalDto.getCarteira(), estadoAvisoLegado)
                .orElse(carteiraDigitalDto.toModel());

        // +1
        if (resultadoAssociacaoCarteiraResponse.isEmpty() || EstadoAssociacaoCarteiraIntegracao.FALHA.equals(resultadoAssociacaoCarteiraResponse.get().getResultado())){
            carteiraDigital.associacaoFalhaCarteira();
        }
        // +1
        else if (EstadoAssociacaoCarteiraIntegracao.ASSOCIADA.equals(resultadoAssociacaoCarteiraResponse.get().getResultado())){
            carteiraDigital.associacaoSucessoCarteira();
        }

        carteiraDigitalRepository.save(carteiraDigital);

        return carteiraDigital;

    }

}
