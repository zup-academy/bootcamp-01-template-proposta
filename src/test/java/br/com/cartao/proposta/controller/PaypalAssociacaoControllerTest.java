package br.com.cartao.proposta.controller;

import br.com.cartao.proposta.domain.dto.CarteiraDigitalDto;
import br.com.cartao.proposta.domain.enums.CarteiraDigitalTipo;
import br.com.cartao.proposta.domain.enums.EstadoAvisoLegado;
import br.com.cartao.proposta.domain.model.Cartao;
import br.com.cartao.proposta.domain.model.CarteiraDigital;
import br.com.cartao.proposta.domain.model.Proposta;
import br.com.cartao.proposta.domain.request.PaypalAssociacaoRequest;
import br.com.cartao.proposta.repository.CarteiraDigitalRepository;
import br.com.cartao.proposta.service.AssociacaoCarteiraCartao;
import br.com.cartao.proposta.service.CarteiraDigitalServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PaypalAssociacaoControllerTest {

    private String idCartao = "1";

    private static EstadoAvisoLegado estadoAvisoLegado= EstadoAvisoLegado.AVISADO;

    private static CarteiraDigitalTipo carteiraDigitalTipo = CarteiraDigitalTipo.PAYPAL;

    private static EntityManager manager;

    private static AssociacaoCarteiraCartao associacaoCarteiraCartao;

    private static CarteiraDigitalServiceImpl carteiraDigitalService;

    private static CarteiraDigitalRepository carteiraDigitalRepository;

    private static PaypalAssociacaoController paypalAssociacaoController;

    @BeforeEach
    void setup(){
        manager = mock(EntityManager.class);
        carteiraDigitalRepository = mock(CarteiraDigitalRepository.class);
        associacaoCarteiraCartao= mock(AssociacaoCarteiraCartao.class);
        carteiraDigitalService = mock(CarteiraDigitalServiceImpl.class);
        paypalAssociacaoController = new PaypalAssociacaoController(manager,associacaoCarteiraCartao,carteiraDigitalRepository);
    }

    @DisplayName("Deve associar carteira digital do paypal ao cartão com sucesso")
    void deveAssociarCarteiraDigitalPaypalAoCartaoComSucesso() throws JsonProcessingException {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        Proposta proposta = new Proposta("documento","email","endereço","nome",new BigDecimal(1000));
        Cartao cartao = new Cartao("1",proposta);
        PaypalAssociacaoRequest paypalAssociacaoRequest = new PaypalAssociacaoRequest("jose@exemplo.com.br");
        CarteiraDigitalDto carteiraDigitalDto = paypalAssociacaoRequest.toDto(cartao);
        // CarteiraDigital carteiraDigital = new CarteiraDigital(paypalAssociacaoRequest.getEmail(),CarteiraDigitalTipo.PAYPAL,cartao);
        CarteiraDigital carteiraDigitalAssociada = carteiraDigitalDto.toModel();

        when(manager.find(Cartao.class,"1")).thenReturn(cartao);
        when(carteiraDigitalRepository.findByCartaoIdAndCarteiraAndEstadoAvisoLegado(idCartao, carteiraDigitalTipo , estadoAvisoLegado))
                .thenReturn(Optional.empty());
        when(associacaoCarteiraCartao.associa(carteiraDigitalDto))
                .thenReturn(carteiraDigitalAssociada);

        ResponseEntity<?> responseEntity = paypalAssociacaoController.associacaoCarteiraPaypal(idCartao, paypalAssociacaoRequest, uriComponentsBuilder);

        Assertions.assertEquals(HttpStatus.CREATED.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    @DisplayName("Não deve associar carteira digital do paypal com cartao nulo e carteira nao associada")
    void naoDeveAssociarCarteiraDigitalComCartaoNuloECarteiraNaoAssociada() throws JsonProcessingException {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        PaypalAssociacaoRequest paypalAssociacaoRequest = new PaypalAssociacaoRequest("jose@exemplo.com.br");

        when(manager.find(Cartao.class,"1")).thenReturn(null);
        when(carteiraDigitalRepository.findByCartaoIdAndCarteiraAndEstadoAvisoLegado(idCartao, carteiraDigitalTipo , estadoAvisoLegado))
                .thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = paypalAssociacaoController.associacaoCarteiraPaypal(idCartao, paypalAssociacaoRequest, uriComponentsBuilder);

        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    @DisplayName("Não deve associar carteira digital do paypal com cartao nulo e carteira ja associada")
    void naoDeveAssociarCarteiraDigitalPaypalComCartaoNuloECarteiraJaAssociada() throws JsonProcessingException {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        PaypalAssociacaoRequest paypalAssociacaoRequest = new PaypalAssociacaoRequest("jose@exemplo.com.br");
        Proposta proposta = new Proposta("documento","email","endereço","nome",new BigDecimal(1000));
        Cartao cartao = new Cartao("1",proposta);
        CarteiraDigital carteiraDigital = new CarteiraDigital("exemplo@exemplo",CarteiraDigitalTipo.PAYPAL,cartao);

        when(manager.find(Cartao.class,"1")).thenReturn(null);
        when(carteiraDigitalRepository.findByCartaoIdAndCarteiraAndEstadoAvisoLegado(idCartao, carteiraDigitalTipo , estadoAvisoLegado))
                .thenReturn(Optional.of(carteiraDigital));

        ResponseEntity<?> responseEntity = paypalAssociacaoController.associacaoCarteiraPaypal(idCartao, paypalAssociacaoRequest, uriComponentsBuilder);

        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    @DisplayName("Não deve associar carteira digital do paypal com cartao valido e carteira ja associada")
    void naoDeveAssociarCarteiraDigitalPaypalComCarteiraJaAssociada() throws JsonProcessingException {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        PaypalAssociacaoRequest paypalAssociacaoRequest = new PaypalAssociacaoRequest("jose@exemplo.com.br");
        Proposta proposta = new Proposta("documento","email","endereço","nome",new BigDecimal(1000));
        Cartao cartao = new Cartao("1",proposta);
        CarteiraDigital carteiraDigital = new CarteiraDigital("exemplo@exemplo",CarteiraDigitalTipo.PAYPAL,cartao);

        when(manager.find(Cartao.class,"1")).thenReturn(cartao);
        when(carteiraDigitalRepository.findByCartaoIdAndCarteiraAndEstadoAvisoLegado(idCartao, carteiraDigitalTipo , estadoAvisoLegado))
                .thenReturn(Optional.of(carteiraDigital));

        ResponseEntity<?> responseEntity = paypalAssociacaoController.associacaoCarteiraPaypal(idCartao, paypalAssociacaoRequest, uriComponentsBuilder);

        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), responseEntity.getStatusCodeValue());
    }

}