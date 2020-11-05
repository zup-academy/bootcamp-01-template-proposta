package br.com.cartao.proposta.repository;

import br.com.cartao.proposta.domain.enums.CarteiraDigitalTipo;
import br.com.cartao.proposta.domain.enums.EstadoAvisoLegado;
import br.com.cartao.proposta.domain.model.CarteiraDigital;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarteiraDigitalRepository extends CrudRepository<CarteiraDigital, String> {

    Optional<CarteiraDigital> findByCartaoIdAndCarteiraAndEstadoAvisoLegado(String cartaoId, CarteiraDigitalTipo carteira, EstadoAvisoLegado estadoAvisoLegado);

}
