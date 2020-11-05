package dev.arielalvesdutra.proposta.services;

import dev.arielalvesdutra.proposta.controllers.dtos.CadastrarBiometriaRequestDTO;
import dev.arielalvesdutra.proposta.entities.Biometria;
import dev.arielalvesdutra.proposta.entities.Cartao;
import dev.arielalvesdutra.proposta.repositories.BiometriaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
public class BiometriaService {

    private final Logger logger = LoggerFactory.getLogger(BiometriaService.class);

    @Autowired
    private BiometriaRepository biometriaRepository;

    @Autowired
    private CartaoService cartaoService;

    public BiometriaService(
            BiometriaRepository biometriaRepository,
            CartaoService cartaoService) {

        this.biometriaRepository = biometriaRepository;
        this.cartaoService = cartaoService;
    }

    public Biometria buscaPeloId(String biometriaId) {
        return biometriaRepository.findById(biometriaId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Biometria de ID " + biometriaId + " não localizada!"));
    }

    @Transactional
    public Biometria cadastrar(
            String cartaoId,
            CadastrarBiometriaRequestDTO requestDTO) {

        logger.info("Buscando Cartão de ID {} para adicionar biometria.", cartaoId);
        Cartao cartao = cartaoService.buscaPeloId(cartaoId);

        Biometria biometria = biometriaRepository.save(requestDTO.paraEntidade(cartao));
        logger.info("Biometria de ID {} adicionada ao cartão de ID {}.", biometria.getId() ,cartaoId);

        return biometria;
    }
}
