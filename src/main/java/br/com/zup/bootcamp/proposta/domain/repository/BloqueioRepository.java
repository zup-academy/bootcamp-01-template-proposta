package br.com.zup.bootcamp.proposta.domain.repository;

import br.com.zup.bootcamp.proposta.domain.entity.Bloqueio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BloqueioRepository extends JpaRepository<Bloqueio, String> {

    Bloqueio findFirstByOrderByBloqueadoEmDesc();

}
