package br.com.proposta.controllers;

import br.com.proposta.models.Enums.StatusAvaliacaoProposta;
import br.com.proposta.models.Proposta;
import br.com.proposta.outrossistema.GerarCartao;
import br.com.proposta.repositories.CartaoRepository;
import br.com.proposta.repositories.PropostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;


@RestController
@RequestMapping("/api/cartoes")
public class NovoCartaoController {

    @Autowired
    private GerarCartao gerarCartao;


    @Autowired
    private CartaoRepository cartaoRepository;


    @Autowired
    private PropostaRepository propostaRepository;


    @PostMapping
    @Scheduled(initialDelay=1000, fixedRate=5000)
    public void geraCartoesAsync(){

        Collection<Proposta> propostasAceitasSemCartao =
                propostaRepository.findByStatusAndCartaoNull(StatusAvaliacaoProposta.ELEGIVEL);

        propostasAceitasSemCartao.forEach(proposta -> {

                gerarCartao.geraCartaoSegundoPlano(proposta, cartaoRepository);

        });
    }
}
