package br.com.proposta.externos;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.proposta.dto.AssociarCateiraConsultaDTO;
import br.com.proposta.dto.AvisoViagemConsultaDTO;
import br.com.proposta.dto.CartaoRetornoDTO;

//Contagem de Pontos - TOTAL:3
//1 - AssociarCateiraConsultaDTO
//1 - AvisoViagemConsultaDTO
//1 - CartaoRetornoDTO

@FeignClient(url = "http://localhost:8888", name = "cartoes")
public interface Cartoes {

	@GetMapping("/api/contas")
	public ResponseEntity<?> criaCartao();
	
	@PostMapping("/api/cartoes/{id}")
	public ResponseEntity<CartaoRetornoDTO> buscaNumeroCartao(@PathVariable("id") String id);
	
	@PostMapping("/api/cartoes/{id}/bloqueios")
	public ResponseEntity<String> bloqueiaCartao(@PathVariable("id") String id, @RequestBody Map<String, String> sistemaResponsavel);
	
	@PostMapping("/api/cartoes/{id}/avisos")
	public ResponseEntity<String> avisoViagem(@PathVariable("id") String id, @RequestBody AvisoViagemConsultaDTO avisoViagemConsulta);
	
	@PostMapping("/api/cartoes/{id}/carteiras")
	public ResponseEntity<String> associarCarteira(@PathVariable("id") String id, @RequestBody AssociarCateiraConsultaDTO associarCarteiraConsulta);
}
