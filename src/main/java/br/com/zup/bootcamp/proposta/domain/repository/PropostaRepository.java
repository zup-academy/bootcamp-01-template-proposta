package br.com.zup.bootcamp.proposta.domain.repository;

import br.com.zup.bootcamp.proposta.domain.entity.Proposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PropostaRepository extends JpaRepository<Proposta, Long> {

    @Query(value = "SELECT documento FROM proposta WHERE documento = ?1", nativeQuery = true)
    Optional<Proposta> findByDocumento(String documento);

}
