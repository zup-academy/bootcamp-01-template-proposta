package br.com.cartao.proposta.service;

import br.com.cartao.proposta.domain.dto.CarteiraDigitalDto;
import br.com.cartao.proposta.domain.model.CarteiraDigital;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface AssociacaoCarteiraCartao {

    CarteiraDigital associa(CarteiraDigitalDto carteiraDigitalDto) throws JsonProcessingException;
}
