package br.com.proposta.controllers;

import br.com.proposta.models.Cartao;
import br.com.proposta.models.Enums.StatusAvaliacaoProposta;
import br.com.proposta.models.Proposta;
import br.com.proposta.services.GerarCartaoService;
import br.com.proposta.repositories.PropostaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;


@RestController
@RequestMapping("/api/cartoes")
public class NovoCartaoController {


    private GerarCartaoService gerarCartaoService;

    private PropostaRepository propostaRepository;

    private final Logger logger = LoggerFactory.getLogger(Proposta.class);


    public NovoCartaoController(GerarCartaoService gerarCartaoService, PropostaRepository propostaRepository) {
        this.gerarCartaoService = gerarCartaoService;
        this.propostaRepository = propostaRepository;
    }


    @PostMapping
    @Scheduled(initialDelay=1000, fixedRate=5000)
    public void geraCartoesAsync(){

        Collection<Proposta> propostasAceitasSemCartao =
                propostaRepository.findByStatusAndCartaoNull(StatusAvaliacaoProposta.ELEGIVEL);

        propostasAceitasSemCartao.forEach(proposta -> {

            gerarCartaoService.geraCartaoSegundoPlano(proposta);

            logger.info("Cartão gerado no serviço de cartões referente à proposta: nome do cliente={}", proposta.getNome());

        });
    }
}
