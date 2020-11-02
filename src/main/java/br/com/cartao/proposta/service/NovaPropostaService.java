package br.com.cartao.proposta.service;

import br.com.cartao.proposta.domain.model.Cartao;
import br.com.cartao.proposta.domain.model.Proposta;
import br.com.cartao.proposta.domain.request.NovaPropostaRequest;
import br.com.cartao.proposta.domain.response.AnalisePropostaResponse;
import br.com.cartao.proposta.domain.response.NovaPropostaResponseDto;
import br.com.cartao.proposta.repository.PropostaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Carga intrínseca máxima permitida - 7
 * Carga intrínseca da classe - 7
 */

@Service
public class NovaPropostaService {

    private static Logger logger = LoggerFactory.getLogger(NovaPropostaService.class);

    //+1
    private final PropostaRepository propostaRepository;
    //+1
    private final AnalisePropostaService analisePropostaService;

    public NovaPropostaService(PropostaRepository propostaRepository, AnalisePropostaService analisePropostaService) {
        this.analisePropostaService = analisePropostaService;
        this.propostaRepository = propostaRepository;
    }

    @Transactional
    // +2
    public NovaPropostaResponseDto criaNovaProposta(NovaPropostaRequest novaPropostaRequest) throws JsonProcessingException {
        // +1
        Proposta proposta = novaPropostaRequest.toModel();

        propostaRepository.save(proposta);
        // +1
        Optional<AnalisePropostaResponse> analisePropostaResponse = analisePropostaService.analisa(proposta);
        // +1
        if (analisePropostaResponse.isPresent()){
            salvaTentativaProposta(proposta, analisePropostaResponse.get());
        }

        NovaPropostaResponseDto novaPropostaResponseDto = new NovaPropostaResponseDto(proposta);

        return novaPropostaResponseDto;
    }

    @Transactional
    private void salvaTentativaProposta( Proposta proposta, AnalisePropostaResponse analisePropostaResponse){
        proposta.adicionaEstadoProposta(analisePropostaResponse);
        propostaRepository.save(proposta);
        logger.info("Proposta atualizada com status da analise financeira: {}", proposta);
    }

    @Transactional
    private void salvaFlagCartaoCriado(Proposta proposta, boolean cartaoCriado) {
        proposta.alteraStatusCartaoCriado(cartaoCriado);
        propostaRepository.save(proposta);
    }

}
