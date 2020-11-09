package br.com.zup.bootcamp.proposta.domain.service.enums;

public enum AnalisePropostaStatus {

    COM_RESTRICAO {
        @Override
        public StatusProposta toPropostaStatus() {
            return StatusProposta.NAO_ELEGIVEL;
        }
    },
    SEM_RESTRICAO {
        @Override
        public StatusProposta toPropostaStatus() {
            return StatusProposta.ELEGIVEL;
        }
    };

    public abstract StatusProposta toPropostaStatus();
}
