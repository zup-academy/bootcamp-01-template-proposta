package br.com.cartao.proposta.service;

import br.com.cartao.proposta.consumer.AssociarCarteiraDigitalConsumer;
import br.com.cartao.proposta.domain.dto.CarteiraDigitalDto;
import br.com.cartao.proposta.domain.model.Cartao;
import br.com.cartao.proposta.domain.request.SolicitacaoInclusaoCarteiraRequest;
import br.com.cartao.proposta.domain.response.ResultadoAssociacaoCarteiraResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarteiraDigitalIntegracaoService {

    private static Logger logger = LoggerFactory.getLogger(CarteiraDigitalIntegracaoService.class);

    private final AssociarCarteiraDigitalConsumer associarCarteiraDigitalConsumer;

    public CarteiraDigitalIntegracaoService(AssociarCarteiraDigitalConsumer associarCarteiraDigitalConsumer) {
        this.associarCarteiraDigitalConsumer = associarCarteiraDigitalConsumer;
    }

    public Optional<ResultadoAssociacaoCarteiraResponse> associar(CarteiraDigitalDto carteiraDigitalDto) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            logger.info("Inicio da tentativa de avisar sistema legado sobre associação de carteira");
            SolicitacaoInclusaoCarteiraRequest solicitacaoInclusaoCarteiraRequest = new
                    SolicitacaoInclusaoCarteiraRequest(carteiraDigitalDto.getEmail(), carteiraDigitalDto.getCarteira());

            ResultadoAssociacaoCarteiraResponse resultadoAssociacaoCarteiraResponse = associarCarteiraDigitalConsumer.associaCarteiraDigital(carteiraDigitalDto.numeroCartao(), solicitacaoInclusaoCarteiraRequest);
            logger.info("Associação feita com sucesso. Resposta: {}", resultadoAssociacaoCarteiraResponse.getResultado());
            return Optional.ofNullable(resultadoAssociacaoCarteiraResponse);
        }catch (FeignException exception){

            if (HttpStatus.UNPROCESSABLE_ENTITY.value() == exception.status()){

                ResultadoAssociacaoCarteiraResponse resultadoAssociacaoCarteiraResponse =
                        objectMapper.readValue(exception.contentUTF8(), ResultadoAssociacaoCarteiraResponse.class);

                logger.info("Tentativa de aviso com falha. Resposta: {}",resultadoAssociacaoCarteiraResponse.getResultado());

                return Optional.ofNullable(resultadoAssociacaoCarteiraResponse);
            }
            logger.warn("Erro na tentativa de associar carteira digital: {}. Erro: {}", carteiraDigitalDto.getCarteira(), exception.getMessage());
            return Optional.empty();
        }
    }
}
