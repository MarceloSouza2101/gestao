package com.marcelo.gestao.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.marcelo.gestao.domain.dto.FornecedorDTO;
import com.marcelo.gestao.domain.dto.FornecedorResumidoDTO;
import com.marcelo.gestao.domain.vo.FornecedorVO;
import com.marcelo.gestao.util.RestPageImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
class FornecedorControllerTest extends AbstractController{
	
	@Autowired
	protected TestRestTemplate restTemplate;

	private static final String FORNECEDORES = "/gestao/fornecedores";
	private static Long ID_FORNECEDOR = null;
	
	private static StringBuilder path = new StringBuilder(URL);
	
	@Test
	@Order(1)
	void salvarForncedor() {
		
		log.info("#INICIO-METRICAS fornecedor-controller.salvarLote");
				
		FornecedorVO fornecedor = gerarFornecedorVO();

		HttpHeaders headers = getHeaders();
		
		HttpEntity<FornecedorVO> entity = new HttpEntity<>(fornecedor, headers);
		
		ParameterizedTypeReference<FornecedorDTO> responseType = new ParameterizedTypeReference<>() {};

		ResponseEntity<FornecedorDTO> response = restTemplate.exchange(path.toString(), HttpMethod.POST, entity, responseType);

		ID_FORNECEDOR = response.getBody().getId();
		
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		
		log.info("#FIM-METRICAS");

	}
	
	@Test
	@Order(2)
	void buscarForncedorPorId() {
		
		log.info("#INICIO-METRICAS fornecedor-controller.buscarFornecedorPorId");
				
		HttpHeaders headers = getHeaders();
		HttpEntity<Void> entity = new HttpEntity<Void>(headers);		
		
		ParameterizedTypeReference<FornecedorDTO> responseType = new ParameterizedTypeReference<>() {};
		
		path.append("/").append(ID_FORNECEDOR);

		ResponseEntity<FornecedorDTO> response = restTemplate.exchange(path.toString(), HttpMethod.GET, entity, responseType);

		assertEquals(ID_FORNECEDOR, response.getBody().getId());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		log.info("#FIM-METRICAS");

	}
	
	@Test
	@Order(3)
	void buscarFornecedorPorFiltro() {
		log.info("#INICIO-METRICAS fornecedor-controller.buscarFornecedorPorFiltros");

		ParameterizedTypeReference<RestPageImpl<FornecedorResumidoDTO>> responseType = new ParameterizedTypeReference<>() {};
		
		path.append("/filtros?nomeFornecedor=Fornecedor&codigoPais=076");
		
		HttpHeaders headers = getHeaders();
		HttpEntity<Void> entity = new HttpEntity<Void>(headers);
		
		ResponseEntity<RestPageImpl<FornecedorResumidoDTO>> response = this.restTemplate
				.exchange(path.toString(), HttpMethod.GET, entity, responseType);

		assertThat(response.getBody()).isNotEmpty();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		log.info("#FIM-METRICAS");
	}
	
	@Test
	@Order(4)
	void alterarForncedorPorId() {
		
		log.info("#INICIO-METRICAS fornecedor-controller.alterarFornecedor");		
				
		path.append("/").append(ID_FORNECEDOR);
		
		FornecedorVO fornecedor = gerarAlterarFornecedorVO();
		
		HttpHeaders headers = getHeaders();
		
		HttpEntity<FornecedorVO> entity = new HttpEntity<>(fornecedor, headers);
		
		ParameterizedTypeReference<FornecedorDTO> responseType = new ParameterizedTypeReference<>() {};

		ResponseEntity<FornecedorDTO> response = restTemplate.exchange(path.toString(), HttpMethod.PUT, entity, responseType);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		log.info("#FIM-METRICAS");
	}
	
	@Test
	@Order(5)
	void naoSalvarDadosJaCadastrados() {
						
		FornecedorVO fornecedor = gerarAlterarFornecedorVO();

		HttpHeaders headers = getHeaders();
		
		HttpEntity<FornecedorVO> entity = new HttpEntity<>(fornecedor, headers);
		
		ParameterizedTypeReference<FornecedorDTO> responseType = new ParameterizedTypeReference<>() {};

		ResponseEntity<FornecedorDTO> response = restTemplate.exchange(path.toString(), HttpMethod.POST, entity, responseType);
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		
	}
	
	@Test
	@Order(6)
	void NaoEncontradoForncedorPorId() {
								
		HttpHeaders headers = getHeaders();
		HttpEntity<Void> entity = new HttpEntity<Void>(headers);		
		
		ParameterizedTypeReference<FornecedorDTO> responseType = new ParameterizedTypeReference<>() {};
		
		path.append("/").append(555L);

		ResponseEntity<FornecedorDTO> response = restTemplate.exchange(path.toString(), HttpMethod.GET, entity, responseType);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		
		
	}
	
	@Test
	@Order(7)
	void naoEncontrarPais() {
						
		FornecedorVO fornecedor = gerarFornecedorVONaoEncontrarPais();

		HttpHeaders headers = getHeaders();
		
		HttpEntity<FornecedorVO> entity = new HttpEntity<>(fornecedor, headers);
		
		ParameterizedTypeReference<FornecedorDTO> responseType = new ParameterizedTypeReference<>() {};

		ResponseEntity<FornecedorDTO> response = restTemplate.exchange(path.toString(), HttpMethod.POST, entity, responseType);
		
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		
	}
	
	@Test
	@Order(8)
	void naoAlterarDadosDuplicadosForncedorPorId() {
		
		log.info("#INICIO-METRICAS fornecedor-controller.alterarFornecedor");		
				
		path.append("/").append(1L);
		
		FornecedorVO fornecedor = gerarAlterarFornecedorVO();
		
		HttpHeaders headers = getHeaders();
		
		HttpEntity<FornecedorVO> entity = new HttpEntity<>(fornecedor, headers);
		
		ParameterizedTypeReference<FornecedorDTO> responseType = new ParameterizedTypeReference<>() {};

		ResponseEntity<FornecedorDTO> response = restTemplate.exchange(path.toString(), HttpMethod.PUT, entity, responseType);
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		
		log.info("#FIM-METRICAS");
	}
	
	@Test
	@Order(9)
	void deletarForncedorPorId() {
		
		log.info("#INICIO-METRICAS fornecedor-controller.deleteFornecedor");
				
		HttpHeaders headers = getHeaders();
		HttpEntity<Void> entity = new HttpEntity<Void>(headers);		
		
		ParameterizedTypeReference<Void> responseType = new ParameterizedTypeReference<>() {};
		
		path.append("/").append(ID_FORNECEDOR);

		ResponseEntity<Void> response = restTemplate.exchange(path.toString(), HttpMethod.DELETE, entity, responseType);

		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		
		log.info("#FIM-METRICAS");

	}

	private FornecedorVO gerarFornecedorVO() {

		return FornecedorVO.builder()
				.nome("Fornecedor Teste cadastro")
				.codigoPais("076")
				.build();
	}

	private FornecedorVO gerarAlterarFornecedorVO() {

		return FornecedorVO.builder()
				.nome("Fornecedor Teste alterar")
				.codigoPais("170")
				.build();
	}
	
	private FornecedorVO gerarFornecedorVONaoEncontrarPais() {

		return FornecedorVO.builder()
				.nome("Fornecedor Teste falha")
				.codigoPais("17000")
				.build();
	}
	
	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

	@BeforeEach
	void inicializar() {
		path.append(FORNECEDORES);
	}

	@AfterEach
	void finalizar() {
		path = new StringBuilder(URL);
	}
}
