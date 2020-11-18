package br.com.cartao.proposta.repository;

import br.com.cartao.proposta.domain.model.Cartao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartaoRepository extends CrudRepository<Cartao, String> {

    Optional<Cartao> findByNumeroCartao(String numeroCartao);
}
