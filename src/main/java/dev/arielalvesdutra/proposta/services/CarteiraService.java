package dev.arielalvesdutra.proposta.services;

import dev.arielalvesdutra.proposta.entities.Cartao;
import dev.arielalvesdutra.proposta.entities.Carteira;
import dev.arielalvesdutra.proposta.entities.enums.CarteiraTipo;
import dev.arielalvesdutra.proposta.http_clients.CartaoHttpClient;
import dev.arielalvesdutra.proposta.http_clients.dtos.SolicitacaoAssociacaoCarteiraDTO;
import dev.arielalvesdutra.proposta.repositories.CarteiraRepository;
import dev.arielalvesdutra.proposta.services.dtos.AssociarCarteiraDTO;
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
public class CarteiraService {

    private final Logger logger = LoggerFactory.getLogger(CartaoService.class);

    @Autowired
    private final CarteiraRepository carteiraRepository;

    @Autowired
    private final CartaoService cartaoService;

    @Autowired
    private final CartaoHttpClient cartaoHttpClient;

    public CarteiraService(
            CarteiraRepository carteiraRepository,
            CartaoService cartaoService,
            CartaoHttpClient cartaoHttpClient) {

        this.carteiraRepository = carteiraRepository;
        this.cartaoService = cartaoService;
        this.cartaoHttpClient = cartaoHttpClient;
    }

    public Carteira buscaPeloId(String carteiraId) {
        return carteiraRepository.findById(carteiraId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Carteira com ID " + carteiraId + " não localizada!"));
    }

    /**
     * Método para associar um cartão com uma carteira digital.
     *
     * @param cartaoId ID do cartão para associar com a carteira digital.
     * @param dto DTO para auxiliar o cadastro de uma carteira digital.
     *
     * @return Carteira cadastrada.
     */
    @Transactional
    public Carteira associa(String cartaoId, AssociarCarteiraDTO dto) {
        logger.info("Buscando cartao de ID {} para criar carteira do tipo {}.", cartaoId, dto.getTipo());
        Cartao cartao = cartaoService.buscaPeloId(cartaoId);

        if (existePeloIdETipo(cartao.getId(), dto.getTipo())) {
            throw new ResponseStatusException(UNPROCESSABLE_ENTITY,
                    "Já existe um carteira do tipo " + dto.getTipo() + " cadastrada para o cartão de ID " + cartaoId + "!");
        }

        try {
            var solicitacao = new SolicitacaoAssociacaoCarteiraDTO()
                    .setEmail(dto.getEmail())
                    .setCarteira(dto.getTipo());
            logger.info("Enviando requisição para associar cartão de ID {} com carteira do tipo {}.",
                    cartaoId, dto.getTipo());
            var resultado = cartaoHttpClient.associaCarteira(cartao.getLegadoId(), solicitacao);

            var carteiraParaSalvar = new Carteira()
                    .setCartao(cartao)
                    .setEmail(dto.getEmail())
                    .setTipo(dto.getTipo())
                    .setLegadoId(resultado.getId());

            logger.info("Cadastrando carteira de tipo {} ao cartão de ID {}.", dto.getTipo(), cartaoId);
            var carteiraSalva = carteiraRepository.save(carteiraParaSalvar);
            logger.info("Cadastrada carteira de ID {} e tipo {} para o cartão de ID {}.",
                    carteiraSalva.getId(), carteiraSalva.getTipo(), cartaoId);

            return carteiraSalva;
        } catch (FeignException e) {
            throw new ResponseStatusException(UNPROCESSABLE_ENTITY,
                    "Falha ao tentar associar cartão de ID " + cartaoId + " com carteira.");
        }
    }

    private boolean existePeloIdETipo(String cartaoId, CarteiraTipo carteiraTipo) {
        return carteiraRepository.existsByCartaoIdAndTipo(cartaoId, carteiraTipo);
    }
}
