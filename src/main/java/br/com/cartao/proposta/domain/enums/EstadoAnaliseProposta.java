package br.com.cartao.proposta.domain.enums;

public enum EstadoAnaliseProposta {

    COM_RESTRICAO{
        @Override
        public EstadoProposta toEstadoProposta() {
            return EstadoProposta.NAO_ELEGIVEL;
        }
    },
    SEM_RESTRICAO{
        @Override
        public EstadoProposta toEstadoProposta() {
            return EstadoProposta.ELEGIVEL;
        }
    };


    public abstract EstadoProposta toEstadoProposta();

    /*public EstadoProposta toNormalize(){
        if (this.equals(COM_RESTRICAO)){
            return EstadoProposta.NAO_ELEGIVEL;
        }else{
            return EstadoProposta.ELEGIVEL;
        }
    }*/
}
