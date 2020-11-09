package br.com.zup.bootcamp.proposta.domain.repository;

import br.com.zup.bootcamp.proposta.domain.entity.Proposta;
import br.com.zup.bootcamp.proposta.domain.service.enums.StatusProposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropostaRepository extends JpaRepository<Proposta, String> {

    Optional<Proposta> findByDocumento(String documento);
    List<Proposta> findByStatusAndCartaoNull(StatusProposta status);

}
