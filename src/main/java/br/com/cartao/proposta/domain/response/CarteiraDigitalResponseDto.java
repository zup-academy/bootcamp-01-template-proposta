package br.com.cartao.proposta.domain.response;

import br.com.cartao.proposta.domain.model.CarteiraDigital;

public class CarteiraDigitalResponseDto {

    private String id;

    @Deprecated
    public CarteiraDigitalResponseDto() {
    }

    public CarteiraDigitalResponseDto(CarteiraDigital carteiraDigitalAssociada) {
        this.id = carteiraDigitalAssociada.getId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
