package br.com.cartao.proposta.service;

import br.com.cartao.proposta.domain.enums.CarteiraDigitalTipo;
import br.com.cartao.proposta.domain.enums.EstadoAvisoLegado;
import br.com.cartao.proposta.domain.model.CarteiraDigital;
import br.com.cartao.proposta.repository.CarteiraDigitalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Carga intrínseca máxima permitida - 7
 * Carga intrínseca da classe - 5
 */

@Service
public class VerificaCarteiraDigital {

    private static Logger logger = LoggerFactory.getLogger(VerificaCarteiraDigital.class);

    // +1
    private static EstadoAvisoLegado estadoAvisoLegado= EstadoAvisoLegado.AVISADO;
    // +1
    private static CarteiraDigitalTipo carteiraDigitalTipo = CarteiraDigitalTipo.SAMSUNG_PAY;

    // +1
    private final CarteiraDigitalRepository carteiraDigitalRepository;

    public VerificaCarteiraDigital(CarteiraDigitalRepository carteiraDigitalRepository) {
        this.carteiraDigitalRepository = carteiraDigitalRepository;
    }

    public boolean verifica(String idCartao){
        // +1
        Optional<CarteiraDigital> carteiraDigitalBuscada = carteiraDigitalRepository.findByCartaoIdAndCarteiraAndEstadoAvisoLegado(idCartao, carteiraDigitalTipo , estadoAvisoLegado);
        // +1
        if (carteiraDigitalBuscada.isPresent()){
            logger.info("Carteira Digital do SamsungPay já associada para cartao com id: {}", idCartao);
            return true;
        }

        return false;
    }
}
