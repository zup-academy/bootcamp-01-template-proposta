package dev.arielalvesdutra.proposta.services;

import dev.arielalvesdutra.proposta.entities.Cartao;
import dev.arielalvesdutra.proposta.entities.CartaoBloqueio;
import dev.arielalvesdutra.proposta.entities.Proposta;
import dev.arielalvesdutra.proposta.entities.enums.CartaoStatus;
import dev.arielalvesdutra.proposta.http_clients.CartaoHttpClient;
import dev.arielalvesdutra.proposta.http_clients.dtos.SolicitacaoBloqueioDTO;
import dev.arielalvesdutra.proposta.repositories.CartaoBloqueioRepository;
import dev.arielalvesdutra.proposta.repositories.CartaoRepository;
import dev.arielalvesdutra.proposta.services.dtos.CadastrarBloqueioDTO;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Service
public class CartaoService {

    @Value("${spring.application.name:propostas}")
    private String nomeDaAplicacao;

    @Value("${spring.profiles.active:desconhecido}")
    private String perfilAtivo;

    private final Logger logger = LoggerFactory.getLogger(CartaoService.class);

    @Autowired
    private final CartaoRepository cartaoRepository;

    @Autowired
    private final CartaoBloqueioRepository cartaoBloqueioRepository;

    @Autowired
    private final PropostaService propostaService;

    @Autowired
    private final CartaoHttpClient cartaoHttpClient;

    public CartaoService(
            CartaoRepository cartaoRepository,
            CartaoBloqueioRepository cartaoBloqueioRepository,
            PropostaService propostaService,
            CartaoHttpClient cartaoHttpClient) {

        this.cartaoRepository = cartaoRepository;
        this.cartaoBloqueioRepository = cartaoBloqueioRepository;
        this.propostaService = propostaService;
        this.cartaoHttpClient = cartaoHttpClient;
    }

    public Cartao buscaPeloId(String cartaoId) {
        return cartaoRepository.findById(cartaoId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Cartão de ID " + cartaoId + " não localizado!"));
    }

    public Cartao buscaPelaPropostaId(String propostaId) {
        return cartaoRepository.findByPropostaId(propostaId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Cartão com Proposta de ID " + propostaId + " não localizado!"));
    }

    /**
     * Envia requisição de bloqueio do cartão para o Serviço de Contas (legado).
     * <p>
     * Caso o bloqueio seja realizado com sucesso no legado, será cadastrado um registro de bloqueio
     * e alterado o status do cartão para bloqueado.
     *
     * @param cartaoId
     * @param cadastrarBloqueioDTO DTO para auxiliar a realização do bloqueio de cartão
     *
     * @return Bloqueio de cartão cadastrado
     */
    @Transactional
    public CartaoBloqueio bloquear(String cartaoId, CadastrarBloqueioDTO cadastrarBloqueioDTO) {
        logger.info("Buscando cartão de ID {} para bloquear.", cartaoId);
        Cartao cartao = buscaPeloId(cartaoId);

        if (cartao.estaBloqueado()) {
            var message = "Já de ID " + cartaoId + " já está bloqueado!";
            logger.warn(message);
            throw new ResponseStatusException(UNPROCESSABLE_ENTITY, message);
        }

        try {
            logger.info("Realizando a requisição de bloqueio do cartão de ID {}.", cartaoId);
            var solicitacaoBloqueioDTO = new SolicitacaoBloqueioDTO(nomeDaAplicacao);
            var resultadoBloqueioDTO = cartaoHttpClient.bloqueiaCartao(cartao.getLegadoId(), solicitacaoBloqueioDTO);


            if (resultadoBloqueioDTO.falhou()) {
                var message = "Falha ao tentar bloquear o cartão de ID " + cartaoId + "!";
                logger.warn(message);
                throw new ResponseStatusException(UNPROCESSABLE_ENTITY, message);
            }

            logger.info("Requisição de bloqueio do cartão de ID {} realizada.", cartaoId);

            var bloqueioParaSalvar = cadastrarBloqueioDTO
                    .paraEntidade()
                    .setCartao(cartao);

            cartao.setStatus(CartaoStatus.BLOQUEADO);
            var bloqueioSalvo = cartaoBloqueioRepository.save(bloqueioParaSalvar);

            logger.info("Cartão de ID {} bloqueado!", cartaoId);
            logger.info("Bloqueio de ID {} para o cartão de ID {} foi realizado!", bloqueioSalvo.getId(), cartaoId);

            return bloqueioSalvo;

        } catch (FeignException e) {
            var message = "Falha ao tentar bloquear o cartão de ID " + cartaoId + "!";
            logger.warn(message);
            throw new ResponseStatusException(UNPROCESSABLE_ENTITY, message);
        }
    }

    /**
     * Sincroniza novos cartões emitidos no legado (Serviço de Cartões) com o sistema.
     * <p>
     * Para cada {@link Proposta} elegivel que não possui {@link Cartao}, busca o
     * Cartão no Serviço de Cartões pelo ID da Proposta.
     * <p>
     * Cada novo Cartão retornado, será registrado no sistema vinculado a Proposta.
     */
    @Transactional
    public void sincronizaNovosCartoesEmitidosNoServicoDeCartoes() {
        logger.info("Buscando propostas elegíveis que não possuem cartão.");
        List<Proposta> propostasElegiveisESemCartao = propostaService.buscaTodasElegiveisESemCartao();
        logger.info("Propostas elegíveis que não possuem cartão buscadas.");

        propostasElegiveisESemCartao.forEach(proposta -> {
            try {
                logger.info("Buscando cartão para a proposta de ID {}.", proposta.getId());
                var cartaoRetornoDTO = cartaoHttpClient.buscaCartao(proposta.getId());

                var cartaoSalvo = cartaoRepository.save(cartaoRetornoDTO.paraEntidade());

                proposta.setCartao(cartaoSalvo);

                logger.info("Cartão de ID {} salvo para prosta de ID {}.", cartaoSalvo.getId(), proposta.getId());
            } catch (FeignException e) {
                logger.warn("Ainda não há cartão para a proposta de ID {}.", proposta.getId());
            }
        });
    }



    @Transactional
    public void removeTodos() {
        if (!perfilAtivo.equals("test")) {
            logger.warn("Ação não permitida!");
            throw new UnsupportedOperationException("Ação não permitida!");
        }

        logger.info("Removendo todos os registros.");
        cartaoRepository.deleteAll();
    }
}
