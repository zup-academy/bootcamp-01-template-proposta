package br.com.proposta.externos;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.proposta.dto.CartaoRetornoDTO;
import br.com.proposta.dto.DocumentoConsultaDTO;
import br.com.proposta.dto.enums.StatusCartao;

@FeignClient(url = "http://localhost:8888", name = "cartoes")
public interface Cartoes {

	@GetMapping("/api/contas")
	public ResponseEntity<?> criaCartao();
	
	@PostMapping("/api/cartoes/{id}")
	public ResponseEntity<CartaoRetornoDTO> buscaNumeroCartao(@PathVariable("id") String id);
	
	@PostMapping("/api/cartoes/{id}/bloqueios")
	public ResponseEntity<String> bloqueiaCartao(@PathVariable("id") String id, @RequestBody Map<String, String> sistemaResponsavel);
}
