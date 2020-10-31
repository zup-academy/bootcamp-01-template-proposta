package br.com.proposta.repositories;

import br.com.proposta.entidades.Senha;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SenhaRepository extends CrudRepository<Senha, String> {
}
