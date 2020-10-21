package com.github.marcoscoutozup.proposta.proposta;

import com.github.marcoscoutozup.proposta.proposta.enums.StatusDaProposta;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PropostaRepository extends CrudRepository<Proposta, UUID> {

    Optional<Proposta> findByDocumento(String documento);

    List<Proposta> findByStatusDaProposta(StatusDaProposta statusDaProposta);
}
