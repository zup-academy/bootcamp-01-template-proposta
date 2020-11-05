package dev.arielalvesdutra.proposta.services;

import dev.arielalvesdutra.proposta.controllers.dtos.CadastrarPropostaDTO;
import dev.arielalvesdutra.proposta.entities.Proposta;
import dev.arielalvesdutra.proposta.http_clients.AnaliseHttpClient;
import dev.arielalvesdutra.proposta.http_clients.dtos.SolicitacaoAnaliseDTO;
import dev.arielalvesdutra.proposta.repositories.PropostaRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

import static dev.arielalvesdutra.proposta.entities.enums.PropostaStatus.ELEGIVEL;
import static dev.arielalvesdutra.proposta.http_clients.dtos.enums.ResultadoAnaliseStatus.COM_RESTRICAO;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Service
public class PropostaService {

    private final Logger logger = LoggerFactory.getLogger(PropostaService.class);

    @Value("${spring.profiles.active:desconhecido}")
    private String perfilAtivo;

    @Autowired
    private AnaliseHttpClient analiseHttpClient;

    @Autowired
    private PropostaRepository propostaRepository;

    public PropostaService(
            PropostaRepository propostaRepository,
            AnaliseHttpClient analiseHttpClient) {

        this.propostaRepository = propostaRepository;
        this.analiseHttpClient = analiseHttpClient;
    }

    public Proposta buscaPeloId(String propostaId) {
        return propostaRepository.findById(propostaId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Proposta com ID " + propostaId + " não localizada!"));
    }

    /**
     * Busca todas as propostas elegiveis que não possuem cartão.
     *
     * @return
     */
    public List<Proposta> buscaTodasElegiveisESemCartao() {
        return propostaRepository.findAllByStatusAndCartao(ELEGIVEL, null);
    }

    @Transactional
    public Proposta cadastrar(CadastrarPropostaDTO request) {
        logger.info("Cadastrando proposta com o documento {}.", request.getDocumento());

        if (existePeloDocumento(request.getDocumento())) {
            var message =  "Já existe uma proposta cadastrada com o documento " + request.getDocumento() + "!";
            logger.warn(message);
            throw new ResponseStatusException(UNPROCESSABLE_ENTITY, message);
        }

        var propostaCadastrada =  propostaRepository.save(request.paraEntidade());
        logger.info("Proposta de ID {} cadastrada.", propostaCadastrada.getId());

        var solicitacaoAnaliseDTO = new SolicitacaoAnaliseDTO(propostaCadastrada);

        try {
            var resultadoAnaliseDTO = analiseHttpClient.analisaDadosFinanceiros(solicitacaoAnaliseDTO);
            propostaCadastrada.aplicaResultadoAnalise(resultadoAnaliseDTO.getResultadoSolicitacao());
            logger.info("Proposta de ID {} atualizada como elegivel.", propostaCadastrada.getId());
        } catch (FeignException.UnprocessableEntity e) {
            propostaCadastrada.aplicaResultadoAnalise(COM_RESTRICAO);
            logger.warn("Proposta de ID {} atualizada como não elegivel.", propostaCadastrada.getId());
        }

        return propostaCadastrada;
    }

    /**
     * Retorna verdadeiro caso exista uma proposta com o documento passado por
     * parâmetro.
     *
     * @param documento
     * @return
     */
    public boolean existePeloDocumento(String documento) {
        Assert.notNull(documento, "Documento para busca da proposta não pode ser nulo!");

        return propostaRepository.existsByDocumento(documento);
    }

    @Transactional
    public void removeTodos() {
        if (!perfilAtivo.equals("test")) {
            logger.warn("Ação não permitida!");
            throw new UnsupportedOperationException("Ação não permitida!");
        }

        logger.info("Removendo todos os registros.");
        propostaRepository.deleteAll();
    }
}
