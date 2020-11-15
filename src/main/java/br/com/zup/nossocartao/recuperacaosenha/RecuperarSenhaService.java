package br.com.zup.nossocartao.recuperacaosenha;

import org.springframework.stereotype.Service;

@Service
public class RecuperarSenhaService {

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
