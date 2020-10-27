package br.com.zup.bootcamp.proposta.controller;

import br.com.zup.bootcamp.proposta.api.controller.CadastraPropostaController;
import br.com.zup.bootcamp.proposta.api.dto.RequestPropostaDto;
import br.com.zup.bootcamp.proposta.api.handler.VerificaDocumentoCpfCnpjValidator;
import br.com.zup.bootcamp.proposta.domain.repository.PropostaRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CadastrarPropostaControllerTest {

    private static final String DOCUMENTO = "01423348478";
    private static final String EMAIL = "carlos@junior";
    private static final String NOME = "Carlos Eduardo";
    private static final String ENDERECO = "bh";
    private static final BigDecimal SALARAIO = BigDecimal.valueOf(500);


    protected static PropostaRepository repository = mock(PropostaRepository.class);
    protected  static RequestPropostaDto  request;
    protected  static CadastraPropostaController controller;
    protected  static VerificaDocumentoCpfCnpjValidator documentoValidator;

    @BeforeAll
    public static void setUp() {
        request = new RequestPropostaDto(DOCUMENTO, EMAIL, NOME, ENDERECO,SALARAIO);
        controller = new CadastraPropostaController(documentoValidator, repository);
        when(repository.findByDocumento(DOCUMENTO)).thenReturn(Optional.empty());

//        when(request.documentoValido()).thenReturn(true);
    }


    @Test
    @DisplayName("Deve salvar um proposta e retornar 201")
    public void teste1(){
        ResponseEntity<?> response = controller.adiciona(request, UriComponentsBuilder.newInstance());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

}
