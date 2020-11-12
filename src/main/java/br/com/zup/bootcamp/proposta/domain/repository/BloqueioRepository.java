package br.com.zup.bootcamp.proposta.domain.repository;

import br.com.zup.bootcamp.proposta.domain.entity.Bloqueio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloqueioRepository extends JpaRepository<Bloqueio, String> {

    Bloqueio findFirstByOrderByBloqueadoEmDesc();

}
