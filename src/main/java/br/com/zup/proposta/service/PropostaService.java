package br.com.zup.proposta.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.zup.proposta.configs.exceptions.ApiException;
import br.com.zup.proposta.controllers.apiResponses.AnaliseResponse;
import br.com.zup.proposta.controllers.apiResponses.KeycloakUser;
import br.com.zup.proposta.controllers.apiResponses.TokenResponse;
import br.com.zup.proposta.controllers.dto.PropostaDto;
import br.com.zup.proposta.controllers.form.AdminTokenRequest;
import br.com.zup.proposta.controllers.form.NovaSenhaUsuarioForm;
import br.com.zup.proposta.controllers.form.NovoUsuarioForm;
import br.com.zup.proposta.model.Proposta;
import br.com.zup.proposta.model.enums.EstadoProposta;
import br.com.zup.proposta.repositories.PropostaRepository;
import br.com.zup.proposta.service.feign.AdminTokenClient;
import br.com.zup.proposta.service.feign.AnaliseClient;
import br.com.zup.proposta.service.feign.KeycloakClient;
import feign.FeignException.UnprocessableEntity;

@Service
public class PropostaService {

    private static final Logger logger = LoggerFactory.getLogger(PropostaService.class);

    @Value("${proposta.encryptors-salt}")
    String salt;
    @Value("${proposta.encryptors-password}")
    String pass;
    @Value("${feign.admin.client-id}")
    String client_id;
    @Value("${feign.admin.client-secret}")
    String client_secret;
    @Value("${feign.admin.grant_type}")
    String grant_type;

    @Autowired
    private PropostaRepository repository;
    @Autowired
    private AnaliseClient analiseClient;
    @Autowired
    private KeycloakClient keycloakClient;
    @Autowired
    private AdminTokenClient adminTokenClient;

    public PropostaDto criar(Proposta proposta) {
        criarNovoUsuario(proposta);
        Proposta propostaCriada = repository.save(proposta);
        logger.info("Proposta criada", propostaCriada.toString());

        try {
            AnaliseResponse analiseResponse = analiseClient.analiseProposta(propostaCriada.toAnaliseForm(pass, salt));
            logger.info("Analise recebida do endpoint", analiseResponse.toString());

            if (analiseResponse.isElegivel()) {
                propostaCriada.setEstadoProposta(EstadoProposta.ELEGIVEL);
                repository.save(propostaCriada);
                logger.info("Proposta marcada como Elegível", propostaCriada.toString());
            }
                        
        } catch (UnprocessableEntity e) {
            if (e.status() == 422) {
                logger.info("Proposta Não Elegível", propostaCriada.toString());
                return propostaCriada.toDto();
            } else {
                logger.info("Erro desconhecido no sistema de análise. Status code recebido: {}", e.status());
            }
        }
        
        logger.info("Retornando DTO");
        return propostaCriada.toDto();
    }

    public Proposta buscarPorId(String id) {
        return repository.findById(id).orElseThrow(() -> 
            new IllegalStateException("Proposta com id " + id + " não encontrada"));
    }

    public void removeTudo() {
        repository.deleteAll();
    }

    private void criarNovoUsuario(Proposta proposta) {
        try {
            TokenResponse adminToken = adminTokenClient.accessToken(new AdminTokenRequest(client_id, client_secret, grant_type));

            keycloakClient.criaUsuario(adminToken.getBearer_token(), new NovoUsuarioForm(proposta.getDocumento(pass, salt)));
            List<KeycloakUser> user = keycloakClient.getUser(adminToken.getBearer_token(), proposta.getDocumento(pass, salt));
            keycloakClient.setPassword(adminToken.getBearer_token(), user.get(0).getId(), new NovaSenhaUsuarioForm("password"));
        } catch (Exception e) {
            logger.info("Não foi possivel registrar novo usuário.");
            throw new ApiException("Não foi possivel registrar novo usuário.\n" + e.getMessage());
        }
    }

}
