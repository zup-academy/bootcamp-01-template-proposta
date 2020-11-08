package dev.arielalvesdutra.proposta.services;


import dev.arielalvesdutra.proposta.entities.AvisoViagem;
import dev.arielalvesdutra.proposta.entities.Cartao;
import dev.arielalvesdutra.proposta.http_clients.CartaoHttpClient;
import dev.arielalvesdutra.proposta.http_clients.dtos.NotificacaoAvisoViagemDTO;
import dev.arielalvesdutra.proposta.repositories.AvisoViagemRepository;
import dev.arielalvesdutra.proposta.services.dtos.CadastrarAvisoViagemDTO;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Service
public class AvisoViagemService {

    private final Logger logger = LoggerFactory.getLogger(AvisoViagemService.class);

    @Autowired
    private final AvisoViagemRepository avisoViagemRepository;

    @Autowired
    private final CartaoService cartaoService;

    @Autowired
    private final CartaoHttpClient cartaoHttpClient;

    public AvisoViagemService(
            AvisoViagemRepository avisoViagemRepository,
            CartaoService cartaoService,
            CartaoHttpClient cartaoHttpClient) {

        this.avisoViagemRepository = avisoViagemRepository;
        this.cartaoService = cartaoService;
        this.cartaoHttpClient = cartaoHttpClient;
    }

    public AvisoViagem buscaPeloId(String avisoId) {
        return avisoViagemRepository.findById(avisoId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Aviso de viagem de ID " + avisoId + " não localizado!"));
    }

    @Transactional
    public AvisoViagem cadastrar(String cartaoId, CadastrarAvisoViagemDTO dto) {
        logger.info("Buscando cartão de ID {} para cadastrar aviso de viagem.", cartaoId);
        Cartao cartao = cartaoService.buscaPeloId(cartaoId);

        try {
            var notificacaoAvisoViagemDTO = new NotificacaoAvisoViagemDTO()
                    .setDestino(dto.getDestino())
                    .setValidoAte(dto.getTermino());

            logger.info("Notificando Serviço de Cartões sobre aviso de viagem para cartao de ID {}.", cartaoId);
            var resultadoAvisoViagem = cartaoHttpClient.notificaAvisoDeViagem(cartao.getLegadoId(), notificacaoAvisoViagemDTO);

            if (resultadoAvisoViagem.falhou()) {
                throw new ResponseStatusException(UNPROCESSABLE_ENTITY, "Falha ao tentar notificar aviso de viagem para o cartão de ID " + cartaoId + "!");
            }

            var avisoParaCadastrar = dto
                    .paraEntidade()
                    .setCartao(cartao);

            var avisoCadastrado = avisoViagemRepository.save(avisoParaCadastrar);
            logger.info("Cadastrado aviso de viagem de ID {}.", avisoCadastrado.getId());

            return avisoCadastrado;
        } catch (FeignException e) {
            throw new ResponseStatusException(UNPROCESSABLE_ENTITY, "Falha ao tentar notificar aviso de viagem para o cartão de ID " + cartaoId + "!");
        }
    }
}
