package br.com.zup.proposta.dto;

import br.com.zup.proposta.model.Proposta;
import br.com.zup.proposta.model.enums.RespostaStatusAvaliacao;
import br.com.zup.proposta.util.CodificarInformacoes;
import br.com.zup.proposta.validations.CpfCnpj;
import org.springframework.security.crypto.encrypt.Encryptors;

import java.util.UUID;

public class AvaliacaoPropostaRequest {

    @CpfCnpj(message = "O documento é inválido")
    private String documento;
    private String nome;
    private UUID idProposta;
    private RespostaStatusAvaliacao resultadoSolicitacao; //1

    @Deprecated
    public AvaliacaoPropostaRequest() {
    }

    public AvaliacaoPropostaRequest(String documento, String nome, UUID idProposta) { //2
        this.documento = documento;
        this.nome = nome;
        this.idProposta = idProposta;
    }

    public String getDocumento() {
        return documento;
    }

    public RespostaStatusAvaliacao getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }

    public String getNome() {
        return nome;
    }

    public UUID getIdProposta() {
        return idProposta;
    }

    @Override
    public String toString() {
        return "AvaliacaoPropostaRequest{" +
                "documento='" + documento + '\'' +
                ", resultadoSolicitacao='" + resultadoSolicitacao + '\'' +
                '}';
    }

}
