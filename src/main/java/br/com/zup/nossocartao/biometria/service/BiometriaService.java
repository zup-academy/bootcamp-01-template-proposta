package br.com.zup.nossocartao.biometria.service;

import static java.util.Base64.getDecoder;
import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.zup.nossocartao.biometria.Biometria;
import br.com.zup.nossocartao.biometria.repository.BiometriaRepository;
import br.com.zup.nossocartao.proposta.repository.PropostaRepository;

//6
@Service
public class BiometriaService {

	// 1
	private final BiometriaRepository biometriaRepository;

	// 1
	private final PropostaRepository propostaRepository;

	final static Logger logger = LoggerFactory.getLogger(BiometriaService.class);

	@Value("${msg.log.biometria.cartao}")
	private String mensagemLogCartao;

	public BiometriaService(BiometriaRepository biometriaRepository, PropostaRepository propostaRepository) {
		this.biometriaRepository = biometriaRepository;
		this.propostaRepository = propostaRepository;
	}

	// 2
	public Optional<Long> salvarBiometria(String biometria, String idCartao) {
		if (!existeIdCartao(idCartao)) {

			logger.info(mensagemLogCartao, false);

			return empty();
		}

		logger.info(mensagemLogCartao, true);

		// 2
		byte[] fingerprint = getDecoder().decode(biometria.getBytes());
		Biometria salvarBiometria = new Biometria(fingerprint, idCartao);
		Biometria biometriaSalva = biometriaRepository.save(salvarBiometria);
		return of(biometriaSalva.getIdBiometria());

	}

	private boolean existeIdCartao(String idCartao) {
		return propostaRepository.existsByNumeroCartao(idCartao);
	}
}
