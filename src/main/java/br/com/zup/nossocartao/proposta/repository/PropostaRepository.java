package br.com.zup.nossocartao.proposta.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.zup.nossocartao.proposta.Proposta;

public interface PropostaRepository extends CrudRepository<Proposta, Long> {

	boolean existsByCpfCnpj(String cpfCnpj);

	boolean existsById(Long idProposta);
}
