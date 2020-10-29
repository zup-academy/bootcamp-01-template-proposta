package dev.arielalvesdutra.proposta.repositories;

import dev.arielalvesdutra.proposta.entities.Cartao;
import dev.arielalvesdutra.proposta.entities.Proposta;
import dev.arielalvesdutra.proposta.entities.enums.PropostaStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PropostaRepository extends CrudRepository<Proposta, String> {

    boolean existsByDocumento(String documento);

    /**
     * Busca todas as propostas com o status e cartão fornecido por parâmetro.
     *
     * Se o parâmetro de cartão for preenchido com nulo, deve retornar todas as propostas
     * sem cartão e com o status preenchido.
     *
     * @param elegivel
     * @param cartao
     * @return
     */
    List<Proposta> findAllByStatusAndCartao(PropostaStatus elegivel, Cartao cartao);
}
