package br.com.proposta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.proposta.model.Cartao;

@Repository
public interface CartaoRepository extends CrudRepository<Cartao, Long>{

	@Query("select c from Cartao c where proposta_id is null")
	List<Cartao> cartaoSemProposta();
}
