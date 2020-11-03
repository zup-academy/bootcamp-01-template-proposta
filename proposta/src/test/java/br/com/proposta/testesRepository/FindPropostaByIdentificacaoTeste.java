package br.com.proposta.testesRepository;

import br.com.proposta.entidades.Enums.StatusAvaliacaoProposta;
import br.com.proposta.entidades.Proposta;
import br.com.proposta.repositories.PropostaRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FindPropostaByIdentificacaoTeste {


    @Autowired
    private PropostaRepository propostaRepository;


    @Test
    public void DeveriaRetornarUmaPropostaPeloId(){

        String propostaId = "6aa6a454-8f21-497e-9fe3-ae35884eb13b";

        Optional<Proposta> proposta =
                propostaRepository.findById(propostaId);


        Assert.assertNotNull(proposta);
        Assert.assertEquals(propostaId, proposta.get().getId());

    }

    @Test
    public void DeveriaRetornarUmaPropostaElegivelSemCartaoGerado(){

        Collection<Proposta> propostas =
                propostaRepository.findByStatusAndCartaoNull(StatusAvaliacaoProposta.ELEGIVEL);

        propostas.forEach(proposta -> {

            Assert.assertEquals(proposta.getNumeroCartao(), null);
            Assert.assertEquals(proposta.getStatus(), StatusAvaliacaoProposta.ELEGIVEL);

        });

    }
}
