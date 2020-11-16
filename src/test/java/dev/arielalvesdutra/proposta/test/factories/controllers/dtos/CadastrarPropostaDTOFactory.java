package dev.arielalvesdutra.proposta.test.factories.controllers.dtos;

import dev.arielalvesdutra.proposta.controllers.dtos.CadastrarPropostaDTO;

import java.math.BigDecimal;

public class CadastrarPropostaDTOFactory {

    /**
     * Factory que representa uma proposta válida para ser cadastrada.
     *
     * @return
     */
    public static CadastrarPropostaDTO propostaValida() {
        var cpfFake = "97240249034";

        return new CadastrarPropostaDTO()
                .setDocumento(cpfFake)
                .setEmail("teste@teste.com")
                .setNome("Geralt of Rivia")
                .setEndereco("Rua Xyz, 114")
                .setSalario(new BigDecimal("3000.00"));
    }

    /**
     * Factory que representa uma proposta que nao atendente as restricoes
     * para cadastro de prposta.
     *
     * Restrições:
     *
     * - Documento que inicie com 3.
     *
     * @return
     */
    public static CadastrarPropostaDTO propostaNaoAtendenteAsRestricoes() {
        var cpfFakeQueNaoAtendeAsRestricoesParaCadastroDeProposta = "39586647099";

        return new CadastrarPropostaDTO()
                .setDocumento(cpfFakeQueNaoAtendeAsRestricoesParaCadastroDeProposta)
                .setEmail("exemplo@exemplo.com")
                .setNome("Geralt of Rivia")
                .setEndereco("Rua Xyz, 114")
                .setSalario(new BigDecimal("3000.00"));
    }
}
