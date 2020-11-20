package br.com.zup.proposta.dao.repository;

import br.com.zup.proposta.model.Proposta;
import br.com.zup.proposta.model.enums.StatusAvaliacaoProposta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;


public interface PropostaRepository extends CrudRepository<Proposta, UUID> {

    @Query("select p from Proposta p left join fetch p.cartao c " +
            "where p.statusAvaliacaoProposta =:status and c.id is null")
    public List<Proposta> buscarPropostasElegiveis(@Param("status") StatusAvaliacaoProposta status);

}
