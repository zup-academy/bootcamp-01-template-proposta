//package io.github.evertocnsouza.rest.controller;
//
//import io.github.evertocnsouza.domain.entity.Proposta;
//import io.github.evertocnsouza.rest.dto.request.PropostaRequest;
//import io.github.evertocnsouza.validation.BloqueiaDocIgualValidator;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.server.ResponseStatusException;
//import org.springframework.web.util.UriComponentsBuilder;
//import javax.persistence.EntityManager;
//import java.math.BigDecimal;
//
//public class PropostaControllerTest {
//
//
//    @Test
//    @DisplayName("nao pode processar proposta com documento igual")
//    void teste1() {
//        EntityManager manager = Mockito.mock(EntityManager.class);
//        BloqueiaDocIgualValidator validadorDocumento = Mockito.mock(BloqueiaDocIgualValidator.class);
//        PropostaController controller = new PropostaController(manager, validadorDocumento);
//        PropostaRequest request = new PropostaRequest("08709225641",
//                "everton@gmail.com", "Everton ", "Zup Uberlandia", new BigDecimal(5000));
//        UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
//
//        Assertions.assertThrows(ResponseStatusException.class, () -> {
//            controller.save(request, builder);
//        });
//    }
//
//    @Test
//    @DisplayName("deve salvar se o documento está válido")
//    void teste2() {
//        EntityManager manager = Mockito.mock(EntityManager.class);
//
//        BloqueiaDocIgualValidator validadorDocumento = Mockito.mock(BloqueiaDocIgualValidator.class);
//        PropostaController controller = new PropostaController(manager, validadorDocumento);
//        PropostaRequest request = new PropostaRequest("08709225641",
//                "everton@gmail.com", "Everton ", "Zup Uberlandia", new BigDecimal(5000));
//
//        UriComponentsBuilder builder =  UriComponentsBuilder.newInstance();
//
//        Mockito.when(validadorDocumento.estaValido(request)).thenReturn(true);
//        ResponseEntity<?> response = controller.save(request, builder);
//
//        Proposta propostaQueDeviaSerGerada = request.toModel();
//        Mockito.verify(manager).persist(propostaQueDeviaSerGerada);
//        Assertions.assertEquals(HttpStatus.CREATED,response.getStatusCode());
//    }
//}
//
