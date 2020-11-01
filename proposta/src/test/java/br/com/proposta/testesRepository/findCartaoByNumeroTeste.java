package br.com.proposta.testesRepository;

import br.com.proposta.entidades.Cartao;
import br.com.proposta.repositories.CartaoRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class findCartaoByNumeroTeste {

    @Autowired
    private CartaoRepository repository;

    @Test
    public void deveriaRetornarUmCartaoAoBuscarPeloNumero(){

        String numeroCartao = "d8eca9f8-51e1-4aca-af94-9a7eb8056954";

        Cartao cartao = repository.findByNumero(numeroCartao);

        Assert.assertNotNull(cartao);
        Assert.assertEquals(numeroCartao, cartao.getNumero());

    }
}
