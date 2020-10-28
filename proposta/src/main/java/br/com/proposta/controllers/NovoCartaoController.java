package br.com.proposta.controllers;

import br.com.proposta.models.Cartao;
import br.com.proposta.models.Enums.StatusAvaliacaoProposta;
import br.com.proposta.models.Proposta;
import br.com.proposta.services.GerarCartaoService;
import br.com.proposta.repositories.CartaoRepository;
import br.com.proposta.repositories.PropostaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;


@RestController
@RequestMapping("/api/cartoes")
public class NovoCartaoController {


    private GerarCartaoService gerarCartaoService;

    private CartaoRepository cartaoRepository;

    private PropostaRepository propostaRepository;

    private final Logger logger = LoggerFactory.getLogger(Proposta.class);


    public NovoCartaoController(GerarCartaoService gerarCartaoService, CartaoRepository cartaoRepository,
                                PropostaRepository propostaRepository) {
        this.gerarCartaoService = gerarCartaoService;
        this.cartaoRepository = cartaoRepository;
        this.propostaRepository = propostaRepository;
    }


    @PostMapping
    @Scheduled(initialDelay=1000, fixedRate=5000)
    public void geraCartoesAsync(){

        Collection<Proposta> propostasAceitasSemCartao =
                propostaRepository.findByStatusAndCartaoNull(StatusAvaliacaoProposta.ELEGIVEL);

        propostasAceitasSemCartao.forEach(proposta -> {

                gerarCartaoService.geraCartaoSegundoPlano(proposta, cartaoRepository);

            logger.info("Cartão gerado no serviço de cartões referente à proposta:" +
                            "nome do cliente={}",
                    proposta.getNome());


        });
    }
}
