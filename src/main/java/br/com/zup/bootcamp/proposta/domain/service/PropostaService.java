package br.com.zup.bootcamp.proposta.domain.service;

import br.com.zup.bootcamp.proposta.api.externalsystem.LegadoAnaliseFinaceira;
import br.com.zup.bootcamp.proposta.domain.entity.Proposta;
import br.com.zup.bootcamp.proposta.domain.repository.PropostaRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PropostaService {

    private final PropostaRepository repository;
    private final LegadoAnaliseFinaceira analiseFinaceira;
    private final Logger logger = LoggerFactory.getLogger(PropostaService.class);

    public PropostaService(PropostaRepository repository, LegadoAnaliseFinaceira analiseFinaceira) {
        this.repository = repository;
        this.analiseFinaceira = analiseFinaceira;
    }

    public Proposta salvaProposta(Proposta proposta) {
        repository.save(proposta);
        logger.info("Proposta {} com final de documento {} salva e em processo de avalição finaceira ", proposta.getId(),
                proposta.getDocumento().substring(proposta.getDocumento().length()-5));

        try {
            var requestAvaliacaoFinaceira = proposta.requestAvaliacaoFinaceira();
            var responseAvaliacaoFinancieira = analiseFinaceira.SolicitaAnalise(requestAvaliacaoFinaceira);

            proposta.setStatus(responseAvaliacaoFinancieira.getResultadoSolicitacao());

        } catch (FeignException e) {
            if (e.status() == 422) {
                proposta.setStatus(StatusProposta.NAO_ELEGIVEL);
            }
            logger.error("Falha na avaliação financeira do documento {} com final {} ", proposta.getId(),
                    proposta.getDocumento().substring(proposta.getDocumento().length()-5));
        }
        return repository.save(proposta);
    }
}