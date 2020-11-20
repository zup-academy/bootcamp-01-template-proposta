package br.com.zup.proposta.model.enums;

import br.com.zup.proposta.dto.AvaliacaoPropostaRequest;

public enum RespostaStatusAvaliacao {

    COM_RESTRICAO{
        @Override
        public StatusAvaliacaoProposta normaliza() {
            return StatusAvaliacaoProposta.NAO_ELEGIVEL;
        }
    },
    SEM_RESTRICAO{
        @Override
        public StatusAvaliacaoProposta normaliza() {
            return StatusAvaliacaoProposta.ELEGIVEL;
        }
    };

    public abstract StatusAvaliacaoProposta normaliza();

}
