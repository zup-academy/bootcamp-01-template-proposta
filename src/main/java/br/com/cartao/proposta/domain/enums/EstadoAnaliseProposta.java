package br.com.cartao.proposta.domain.enums;

public enum EstadoAnaliseProposta {

    COM_RESTRICAO,
    SEM_RESTRICAO;

    public EstadoProposta toNormalize(){
        if (this.equals(COM_RESTRICAO)){
            return EstadoProposta.NAO_ELEGIVEL;
        }else{
            return EstadoProposta.ELEGIVEL;
        }
    }
}
