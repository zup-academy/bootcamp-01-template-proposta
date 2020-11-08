package dev.arielalvesdutra.proposta.services;

import dev.arielalvesdutra.proposta.entities.Cartao;
import dev.arielalvesdutra.proposta.entities.SolicitacaoRecuperacaoSenha;
import dev.arielalvesdutra.proposta.repositories.SolicitacaoRecuperacaoSenhaRepository;
import dev.arielalvesdutra.proposta.services.dtos.CadastrarSolicitacaoRecuperacaoSenhaDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
public class SolicitacaoRecuperacaoSenhaService {

    private final Logger logger = LoggerFactory.getLogger(SolicitacaoRecuperacaoSenhaService.class);

    @Autowired
    private final SolicitacaoRecuperacaoSenhaRepository solicitacaoRecuperacaoSenhaRepository;

    @Autowired
    private final CartaoService cartaoService;

    public SolicitacaoRecuperacaoSenhaService(
            SolicitacaoRecuperacaoSenhaRepository solicitacaoRecuperacaoSenhaRepository,
            CartaoService cartaoService) {

        this.solicitacaoRecuperacaoSenhaRepository = solicitacaoRecuperacaoSenhaRepository;
        this.cartaoService = cartaoService;
    }

    public SolicitacaoRecuperacaoSenha buscaPeloId(String solicitacaoId) {
        return solicitacaoRecuperacaoSenhaRepository.findById(solicitacaoId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Solicitação de recuperação de senha de ID " + solicitacaoId + " não localizada!"));
    }

    @Transactional
    public SolicitacaoRecuperacaoSenha cadastrar(
            String cartaoId,
            CadastrarSolicitacaoRecuperacaoSenhaDTO dto) {
        logger.info("Buscando o cartão de ID {} para cadastrar uma soliciação de recuperação de senha.", cartaoId);
        Cartao cartao = cartaoService.buscaPeloId(cartaoId);

        SolicitacaoRecuperacaoSenha solicitacaoParaSalvar = dto
                .paraEntidade()
                .setCartao(cartao);

        logger.info("Cadastrando solicitação de recuperação de senha para o cartão de ID {}.", cartaoId);
        var solicitacaoCadastrada =  solicitacaoRecuperacaoSenhaRepository.save(solicitacaoParaSalvar);
        logger.info("Cadastrada solicitação de recuperação de senha de ID {} para o cartão de ID {}.", solicitacaoCadastrada.getId(), cartaoId);

        return solicitacaoCadastrada;
    }
}
