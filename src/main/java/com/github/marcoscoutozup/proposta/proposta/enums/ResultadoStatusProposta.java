package com.github.marcoscoutozup.proposta.proposta.enums;

public enum ResultadoStatusProposta {

    COM_RESTRICAO {
        @Override
        public StatusDaProposta toStatusDaProposta() {
            return StatusDaProposta.NAO_ELEGIVEL;
        }
    },

    SEM_RESTRICAO{
        @Override
        public StatusDaProposta toStatusDaProposta() {
            return StatusDaProposta.ELEGIVEL;
        }
    };

    public abstract StatusDaProposta toStatusDaProposta();
}
