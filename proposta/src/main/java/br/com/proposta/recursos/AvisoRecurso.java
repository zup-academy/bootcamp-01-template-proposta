package br.com.proposta.recursos;

import br.com.proposta.transferenciadados.requisicoes.RequisicaoAvisarViagem;
import br.com.proposta.entidades.Aviso;
import br.com.proposta.repositorios.AvisoRepository;
import br.com.proposta.integracoes.IntegracaoApiCartoes;
import br.com.proposta.compartilhado.BuscarIPeUserAgentNaRequisicao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/viagens")
public class AvisoRecurso {

    /* total de pontos = 6 */

    /* @complexidade - classe criada no projeto */
    private final AvisoRepository avisoRepository;

    /* @complexidade - classe criada no projeto */
    private final IntegracaoApiCartoes integracaoApiCartoes;

    /* @complexidade - classe criada no projeto */
    private final BuscarIPeUserAgentNaRequisicao buscarIPeUserAgentNaRequisicao;


    public AvisoRecurso(AvisoRepository avisoRepository, IntegracaoApiCartoes integracaoApiCartoes,
                        BuscarIPeUserAgentNaRequisicao buscarIPeUserAgentNaRequisicao) {
        this.avisoRepository = avisoRepository;
        this.integracaoApiCartoes = integracaoApiCartoes;
        this.buscarIPeUserAgentNaRequisicao = buscarIPeUserAgentNaRequisicao;
    }

    @PostMapping("/{idCartao}")
    public ResponseEntity<?> avisa(@PathVariable String idCartao, @RequestBody RequisicaoAvisarViagem requisicaoAvisarViagem,
                            HttpServletRequest httpRequest){

        /* @complexidade - utilizando duas classes criada no projeto */
        var resposta = integracaoApiCartoes.avisarViagem(idCartao, requisicaoAvisarViagem).getBody();

        /* @complexidade - utilizando classe criada no projeto */
        var userAgentEip = buscarIPeUserAgentNaRequisicao.recuperarUserAgentEInternetProtocolNaRequisicao(httpRequest);

        /* @complexidade - instanciando classe criada no projeto */
        var novoAviso = new Aviso(idCartao, userAgentEip, resposta);

        avisoRepository.save(novoAviso);

        return ResponseEntity
                .ok()
                .build();

    }
}
