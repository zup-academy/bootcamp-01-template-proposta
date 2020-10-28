package br.com.zup.proposta.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zup.proposta.model.Proposta;

public interface PropostaRepository extends JpaRepository<Proposta, String> {
    Optional<Proposta> findByDocumento(String documento);
}
