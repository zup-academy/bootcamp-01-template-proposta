package com.github.marcoscoutozup.proposta.proposta;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface PropostaRepository extends CrudRepository<Proposta, UUID> {

    Optional<Proposta> findByDocumento(String documento);

}
