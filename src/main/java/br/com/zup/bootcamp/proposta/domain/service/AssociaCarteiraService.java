package br.com.zup.bootcamp.proposta.domain.service;

import br.com.zup.bootcamp.proposta.api.dto.RequestCarteiraDto;
import br.com.zup.bootcamp.proposta.api.externalsystem.LegadoSistemaCartao;
import br.com.zup.bootcamp.proposta.domain.entity.Cartao;
import br.com.zup.bootcamp.proposta.domain.entity.Carteira;
import br.com.zup.bootcamp.proposta.domain.repository.CarteiraRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Service
public class AssociaCarteiraService {

    private final Logger logger = LoggerFactory.getLogger(AssociaCarteiraService.class);

    @Autowired      //1
    private LegadoSistemaCartao sistemaCarta;

    @Autowired      //1
    private CarteiraRepository carteiraRepository;

    @PersistenceContext
    private EntityManager manager;

    @Transactional                               //1             //1                     //1
    public ResponseEntity<?> processaCarteira(Cartao cartao, Carteira carteira, RequestCarteiraDto request, UriComponentsBuilder uri){
        //1
        try {
            sistemaCarta.associarCarteira(cartao.getIdCartaoEmitido(), request);
            cartao.adicionarCarteira(carteira);
            manager.merge(cartao);
        } //1
        catch (FeignException e){
            logger.error("Erro ao associar carteira {} ao cartao id {} ", carteira.getCarteira(), cartao.getId());
            return ResponseEntity.unprocessableEntity().build();
        }
        var ultimaCarteira = carteiraRepository.findFirstByOrderBySolicitadoEmDesc();
        logger.info("Associar carteira {} realizado com sucesso. Cartao id {}, Carteria id{}", carteira.getCarteira(),
                cartao.getId(), ultimaCarteira.getId());

        return ResponseEntity.created(uri.path("/carteiras/{id}")
                .buildAndExpand(ultimaCarteira.getId())
                .toUri()).build();
    }
}
