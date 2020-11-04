package br.com.zup.cartaoproposta.entities.cartao;

import br.com.zup.cartaoproposta.entities.cartao.aviso.AvisoCartaoRetorno;
import br.com.zup.cartaoproposta.entities.cartao.bloqueio.BloqueioCartaoRetorno;
import br.com.zup.cartaoproposta.entities.cartao.carteira.CarteiraCartaoRetorno;
import br.com.zup.cartaoproposta.entities.cartao.parcela.ParcelaCartaoRetorno;
import br.com.zup.cartaoproposta.entities.cartao.renegociacao.RenegociacaoCartaoRetorno;
import br.com.zup.cartaoproposta.entities.cartao.vencimento.VencimentoCartaoRetorno;
import br.com.zup.cartaoproposta.entities.proposta.Proposta;
import br.com.zup.cartaoproposta.repositories.PropostaRepository;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Contagem de carga intrínseca da classe: 9
 */

public class DadosCartaoRetorno {

    private String id;
    private LocalDateTime emitidoEm;
    private String titular;
    //1
    private List<BloqueioCartaoRetorno> bloqueios;
    //1
    private List<AvisoCartaoRetorno> avisos;
    //1
    private List<CarteiraCartaoRetorno> carteiras;
    //1
    private List<ParcelaCartaoRetorno> parcelas;
    private int limite;
    //1
    private RenegociacaoCartaoRetorno renegociacao;
    //1
    private VencimentoCartaoRetorno vencimento;
    private String idProposta;

    @Deprecated
    DadosCartaoRetorno(){}

    public DadosCartaoRetorno(String id, LocalDateTime emitidoEm, String titular, List<BloqueioCartaoRetorno> bloqueios, List<AvisoCartaoRetorno> avisos, List<CarteiraCartaoRetorno> carteiras, List<ParcelaCartaoRetorno> parcelas, int limite, RenegociacaoCartaoRetorno renegociacao, VencimentoCartaoRetorno vencimento, String idProposta) {
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

    public List<BloqueioCartaoRetorno> getBloqueios() {
        return bloqueios;
    }

    public List<AvisoCartaoRetorno> getAvisos() {
        return avisos;
    }

    public List<CarteiraCartaoRetorno> getCarteiras() {
        return carteiras;
    }

    public List<ParcelaCartaoRetorno> getParcelas() {
        return parcelas;
    }

    public int getLimite() {
        return limite;
    }

    public RenegociacaoCartaoRetorno getRenegociacao() {
        return renegociacao;
    }

    public VencimentoCartaoRetorno getVencimento() {
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
