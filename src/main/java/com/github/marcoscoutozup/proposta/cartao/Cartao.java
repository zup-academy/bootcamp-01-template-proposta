package com.github.marcoscoutozup.proposta.cartao;

import com.github.marcoscoutozup.proposta.avisos.Aviso;
import com.github.marcoscoutozup.proposta.biometria.Biometria;
import com.github.marcoscoutozup.proposta.bloqueio.Bloqueio;
import com.github.marcoscoutozup.proposta.bloqueio.enums.EstadoCartao;
import com.github.marcoscoutozup.proposta.carteira.Carteira;
import com.github.marcoscoutozup.proposta.carteira.enums.TipoCarteira;
import com.github.marcoscoutozup.proposta.proposta.Proposta;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static com.github.marcoscoutozup.proposta.security.JwtDecoder.retornarEmailDoToken;

@Entity
public class Cartao {

    @Id
    @GeneratedValue(generator = "uuid4")
    private UUID id;

    @NotNull
    private UUID numeroCartao;

    @NotNull
    private LocalDateTime emitidoEm;

    @OneToMany  //1
    private Set<Biometria> biometrias;

    @OneToMany  //2
    private Set<Bloqueio> bloqueios;

    @OneToMany  //3
    private Set<Aviso> avisos;

    @OneToMany //4
    private Set<Carteira> carteiras;

    @OneToOne //5
    private Proposta proposta;

    @Enumerated(EnumType.STRING) //6
    private EstadoCartao estadoCartao;

    @Deprecated
    public Cartao() {
    }

    public Cartao(UUID numeroCartao, LocalDateTime emitidoEm) {
        this.numeroCartao = numeroCartao;
        this.emitidoEm = emitidoEm;
        this.estadoCartao = EstadoCartao.DESBLOQUEADO;
    }

    public void incluirBiometriaNoCartao(Biometria biometria) {
        Assert.notNull(biometria, "A biometria não pode ser nula para associação do cartão");
        biometrias.add(biometria);
    }

    public void incluirBloqueioDoCartao(Bloqueio bloqueio){
        Assert.notNull(bloqueio, "O bloqueio do cartão não pode ser nulo");
        bloqueios.add(bloqueio);
    }

    public void incluirAvisoDeViagem(Aviso aviso){
        Assert.notNull(aviso, "O aviso não pode ser nulo para associação com o cartão");
        avisos.add(aviso);
    }

    public void incluirCarteira(Carteira carteira){
        Assert.notNull(carteira, "A carteira não pode ser nula para associação com o cartão");
        carteiras.add(carteira);
    }

    public void incluirPropostaNoCartao(Proposta proposta){
        Assert.notNull(proposta, "A proposta não pode ser nula para associação com o cartão");
        this.proposta = proposta;
    }

    public boolean verificarSeJaExisteAssociacaoDaCarteiraComOCartao(TipoCarteira tipoCarteira){
        Assert.notNull(tipoCarteira, "A carteira não pode ser nula");
                                                    //7
        return carteiras.stream().anyMatch(carteira -> carteira.verificarParidadeDeCarteira(tipoCarteira));
    }

    public void bloquearCartao(){
        this.estadoCartao = EstadoCartao.BLOQUEADO;
    }

    public boolean verificarSeOCartaoEstaBloqueado(){
        return estadoCartao.equals(EstadoCartao.BLOQUEADO);
    }

    public boolean verificarSeOEmailDoTokenEOMesmoDoCartao(String token){
                                //8
        String emailDoToken = retornarEmailDoToken(token);
        return this.proposta.getEmail().equals(emailDoToken);
    }

    public UUID getNumeroCartao() {
        return numeroCartao;
    }

    public UUID getId() {
        return id;
    }
}
