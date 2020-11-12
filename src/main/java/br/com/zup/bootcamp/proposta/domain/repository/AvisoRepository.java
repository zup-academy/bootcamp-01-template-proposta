package br.com.zup.bootcamp.proposta.domain.repository;

import br.com.zup.bootcamp.proposta.domain.entity.Aviso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvisoRepository extends JpaRepository<Aviso, String> {
    Aviso findFirstByOrderBySolicitadoEmDesc();

}
