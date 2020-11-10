package br.com.itau.cartaobrancoproposta.service;

import br.com.itau.cartaobrancoproposta.client.CartaoClient;
import br.com.itau.cartaobrancoproposta.error.ApiErrorException;
import br.com.itau.cartaobrancoproposta.model.CarteiraDigital;
import br.com.itau.cartaobrancoproposta.model.SolicitacaoCarteira;
import br.com.itau.cartaobrancoproposta.model.SolicitacaoCarteiraRequest;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CarteiraDigitalService {

    private final Logger logger = LoggerFactory.getLogger(CarteiraDigital.class);
//1
    private final CartaoClient cartaoClient;

    public CarteiraDigitalService(CartaoClient cartaoClient) {
        this.cartaoClient = cartaoClient;
    }

    public void cadastra(String numeroCartao, CarteiraDigital carteiraDigital) { //1
        SolicitacaoCarteira solicitacaoCarteira; //1
        try { //1
            solicitacaoCarteira = cartaoClient.cadastraCarteira(numeroCartao, new SolicitacaoCarteiraRequest(carteiraDigital.getEmail(), carteiraDigital.getEmissor())); //1
            carteiraDigital.associaSolicitacao(solicitacaoCarteira);
        } catch (FeignException feignException) { //1
            logger.error("Erro na requisição da carteira digital do cartão com final {}. Motivo: {}", numeroCartao.substring(24), feignException.getMessage());
            throw new ApiErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "Ocorreu um problema na requisição da carteira digital"); //1
        }
    }
}
