package br.com.proposta.repositories;

import br.com.proposta.entidades.Cartao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartaoRepository extends CrudRepository<Cartao, String> {
    Cartao findByNumero(String id);
}
