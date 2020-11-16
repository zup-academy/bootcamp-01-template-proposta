package br.com.zup.cartaoproposta.entities.cartao;

import br.com.zup.cartaoproposta.entities.cartao.aviso.AvisoCartaoRetornoLegado;
import br.com.zup.cartaoproposta.entities.cartao.bloqueio.BloqueioCartaoRetornoLegado;
import br.com.zup.cartaoproposta.entities.cartao.carteira.CarteiraCartaoRetornoLegado;
import br.com.zup.cartaoproposta.entities.cartao.parcela.ParcelaCartaoRetornoLegado;
import br.com.zup.cartaoproposta.entities.cartao.renegociacao.RenegociacaoCartaoRetornoLegado;
import br.com.zup.cartaoproposta.entities.cartao.vencimento.VencimentoCartaoRetornoLegado;
import br.com.zup.cartaoproposta.entities.proposta.Proposta;
import br.com.zup.cartaoproposta.repositories.PropostaRepository;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Contagem de carga intrínseca da classe: 9
 */

public class DadosCartaoRetornoLegado {

    private String id;
    private LocalDateTime emitidoEm;
    private String titular;
    //1
    private List<BloqueioCartaoRetornoLegado> bloqueios;
    //1
    private List<AvisoCartaoRetornoLegado> avisos;
    //1
    private List<CarteiraCartaoRetornoLegado> carteiras;
    //1
    private List<ParcelaCartaoRetornoLegado> parcelas;
    private int limite;
    //1
    private RenegociacaoCartaoRetornoLegado renegociacao;
    //1
    private VencimentoCartaoRetornoLegado vencimento;
    private String idProposta;

    @Deprecated
    DadosCartaoRetornoLegado(){}

    public DadosCartaoRetornoLegado(String id, LocalDateTime emitidoEm, String titular, List<BloqueioCartaoRetornoLegado> bloqueios, List<AvisoCartaoRetornoLegado> avisos, List<CarteiraCartaoRetornoLegado> carteiras, List<ParcelaCartaoRetornoLegado> parcelas, int limite, RenegociacaoCartaoRetornoLegado renegociacao, VencimentoCartaoRetornoLegado vencimento, String idProposta) {
        this.id = id;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.bloqueios = bloqueios;
        this.avisos = avisos;
        this.carteiras = carteiras;
        this.parcelas = parcelas;
        this.limite = limite;
        this.renegociacao = renegociacao;
        this.vencimento = vencimento;
        this.idProposta = idProposta;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public String getTitular() {
        return titular;
    }

    public List<BloqueioCartaoRetornoLegado> getBloqueios() {
        return bloqueios;
    }

    public List<AvisoCartaoRetornoLegado> getAvisos() {
        return avisos;
    }

    public List<CarteiraCartaoRetornoLegado> getCarteiras() {
        return carteiras;
    }

    public List<ParcelaCartaoRetornoLegado> getParcelas() {
        return parcelas;
    }

    public int getLimite() {
        return limite;
    }

    public RenegociacaoCartaoRetornoLegado getRenegociacao() {
        return renegociacao;
    }

    public VencimentoCartaoRetornoLegado getVencimento() {
        return vencimento;
    }

    public String getIdProposta() {
        return idProposta;
    }

    //2
    public Cartao toModel(PropostaRepository propostaRepository) {

        //1
        Proposta proposta = propostaRepository.getOne(idProposta);
        Assert.notNull(proposta, "Proposta inválida");
        Assert.notNull(vencimento, "Vencimento inválido");

        return new Cartao(id, emitidoEm, titular, limite, vencimento.toModel(), proposta);
    }
}
