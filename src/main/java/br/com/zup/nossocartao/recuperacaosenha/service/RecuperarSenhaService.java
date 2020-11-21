package br.com.zup.nossocartao.recuperacaosenha.service;

import org.springframework.stereotype.Service;

import br.com.zup.nossocartao.recuperacaosenha.RecuperarSenha;
import br.com.zup.nossocartao.recuperacaosenha.repository.RecuperaSenhaRepository;

@Service
public class RecuperarSenhaService {

	// 1
	private final RecuperaSenhaRepository recuperarSenhaRepository;

	public RecuperarSenhaService(RecuperaSenhaRepository recuperarSenha) {
		this.recuperarSenhaRepository = recuperarSenha;
	}

	public Long recuperarSenha(String idCartao, String ipCliente, String userAgent) {
		RecuperarSenha dadosRecuperarSenha = new RecuperarSenha(idCartao, ipCliente, userAgent);

		RecuperarSenha dadosSalvo = recuperarSenhaRepository.save(dadosRecuperarSenha);

		return dadosSalvo.getId();

	}
}
