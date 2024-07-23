package com.marcelo.gestao.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

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

import com.marcelo.gestao.domain.dto.ProdutoDTO;
import com.marcelo.gestao.domain.dto.ProdutoResumidoDTO;
import com.marcelo.gestao.domain.vo.CategoriaVO;
import com.marcelo.gestao.domain.vo.FornecedorProdutoVO;
import com.marcelo.gestao.domain.vo.ProdutoAlteraVO;
import com.marcelo.gestao.domain.vo.ProdutoVO;
import com.marcelo.gestao.util.RestPageImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
class ProdutoControllerTest extends AbstractController{
	
	@Autowired
	protected TestRestTemplate restTemplate;

	private static final String PRODUTOS = "/gestao/produtos";
	private static Long ID_PRODUTOS = null;
	
	private static StringBuilder path = new StringBuilder(URL);
	
	@Test
	@Order(1)
	void salvarProduto() {
		
		log.info("#INICIO-METRICAS produto-controller.salvarLote");
				
		ProdutoVO produto = gerarProdutoVO();

		HttpHeaders headers = getHeaders();
		
		HttpEntity<ProdutoVO> entity = new HttpEntity<>(produto, headers);
		
		ParameterizedTypeReference<ProdutoDTO> responseType = new ParameterizedTypeReference<>() {};

		ResponseEntity<ProdutoDTO> response = restTemplate.exchange(path.toString(), HttpMethod.POST, entity, responseType);

		ID_PRODUTOS = response.getBody().getId();
		
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		
		log.info("#FIM-METRICAS");

	}
	
	@Test
	@Order(2)
	void buscarProdutoPorId() {
		
		log.info("#INICIO-METRICAS produto-controller.buscarProdutoPorId");
				
		HttpHeaders headers = getHeaders();
		HttpEntity<Void> entity = new HttpEntity<Void>(headers);		
		
		ParameterizedTypeReference<ProdutoDTO> responseType = new ParameterizedTypeReference<>() {};
		
		path.append("/").append(ID_PRODUTOS);

		ResponseEntity<ProdutoDTO> response = restTemplate.exchange(path.toString(), HttpMethod.GET, entity, responseType);

		assertEquals(ID_PRODUTOS, response.getBody().getId());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		log.info("#FIM-METRICAS");

	}
	
	@Test
	@Order(3)
	void buscarProdutoPorFiltro() {
		log.info("#INICIO-METRICAS produto-controller.buscarProdutoPorFiltros");

		ParameterizedTypeReference<RestPageImpl<ProdutoResumidoDTO>> responseType = new ParameterizedTypeReference<>() {};
		
		path.append("/filtros?nomeProduto=Limão&quantidade=4&idCategoria=3&idFornecedor=1");
		
		HttpHeaders headers = getHeaders();
		HttpEntity<Void> entity = new HttpEntity<Void>(headers);
		
		ResponseEntity<RestPageImpl<ProdutoResumidoDTO>> response = this.restTemplate
				.exchange(path.toString(), HttpMethod.GET, entity, responseType);
		
		assertThat(response.getBody()).isNotEmpty();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		log.info("#FIM-METRICAS");
	}
	
	@Test
	@Order(4)
	void alterarProdutoPorId() {
		
		log.info("#INICIO-METRICAS produto-controller.alterarProduto");		
				
		path.append("/").append(ID_PRODUTOS);
		
		ProdutoAlteraVO produto = gerarAlterarProdutoVO();
		
		HttpHeaders headers = getHeaders();
		
		HttpEntity<ProdutoAlteraVO> entity = new HttpEntity<>(produto, headers);
		
		ParameterizedTypeReference<ProdutoDTO> responseType = new ParameterizedTypeReference<>() {};

		ResponseEntity<ProdutoDTO> response = restTemplate.exchange(path.toString(), HttpMethod.PUT, entity, responseType);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		log.info("#FIM-METRICAS");
	}
	
	@Test
	@Order(5)
	void naoEncontrarProdutoPorId() {
						
		HttpHeaders headers = getHeaders();
		HttpEntity<Void> entity = new HttpEntity<Void>(headers);		
		
		ParameterizedTypeReference<ProdutoDTO> responseType = new ParameterizedTypeReference<>() {};
		
		path.append("/").append("444");

		ResponseEntity<ProdutoDTO> response = restTemplate.exchange(path.toString(), HttpMethod.GET, entity, responseType);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		
	}
	
	@Test
	@Order(6)
	void naoEncontraFornecedor() {
						
		ProdutoVO produto = gerarProdutoVONaoEncontraFornecedor();

		HttpHeaders headers = getHeaders();
		
		HttpEntity<ProdutoVO> entity = new HttpEntity<>(produto, headers);
		
		ParameterizedTypeReference<ProdutoDTO> responseType = new ParameterizedTypeReference<>() {};

		ResponseEntity<ProdutoDTO> response = restTemplate.exchange(path.toString(), HttpMethod.POST, entity, responseType);
		
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		

	}
	
	@Test
	@Order(7)
	void naoEncontraCategoria() {
						
		ProdutoVO produto = gerarProdutoVONaoEncontraCategoria();

		HttpHeaders headers = getHeaders();
		
		HttpEntity<ProdutoVO> entity = new HttpEntity<>(produto, headers);
		
		ParameterizedTypeReference<ProdutoDTO> responseType = new ParameterizedTypeReference<>() {};

		ResponseEntity<ProdutoDTO> response = restTemplate.exchange(path.toString(), HttpMethod.POST, entity, responseType);
		
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		
	}
	
	@Test
	@Order(8)
	void dadosJaCadastrado() {
						
		ProdutoVO produto = gerarProdutoVO();

		HttpHeaders headers = getHeaders();
		
		HttpEntity<ProdutoVO> entity = new HttpEntity<>(produto, headers);
		
		ParameterizedTypeReference<ProdutoDTO> responseType = new ParameterizedTypeReference<>() {};

		ResponseEntity<ProdutoDTO> response = restTemplate.exchange(path.toString(), HttpMethod.POST, entity, responseType);
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		
	}
	
	@Test
	@Order(9)
	void deletarProdutoPorId() {
		
		log.info("#INICIO-METRICAS produto-controller.deleteProduto");
				
		HttpHeaders headers = getHeaders();
		HttpEntity<Void> entity = new HttpEntity<Void>(headers);		
		
		ParameterizedTypeReference<Void> responseType = new ParameterizedTypeReference<>() {};
		
		path.append("/").append(ID_PRODUTOS);

		ResponseEntity<Void> response = restTemplate.exchange(path.toString(), HttpMethod.DELETE, entity, responseType);

		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		
		log.info("#FIM-METRICAS");

	}

	private ProdutoVO gerarProdutoVO() {

		return ProdutoVO.builder()
				.nome("Limão")
				.descricao("Kg de limão")
				.preco(BigDecimal.ONE)
				.quantidade(4L)
				.categoria(CategoriaVO.builder().id(3L).build())
				.fornecedor(FornecedorProdutoVO.builder().id(1L).build())
				.build();
	}
	
	private ProdutoVO gerarProdutoVONaoEncontraFornecedor() {

		return ProdutoVO.builder()
				.nome("Limão")
				.descricao("Kg de limão")
				.preco(BigDecimal.ONE)
				.quantidade(4L)
				.categoria(CategoriaVO.builder().id(3L).build())
				.fornecedor(FornecedorProdutoVO.builder().id(3331L).build())
				.build();
	}
	
	private ProdutoVO gerarProdutoVONaoEncontraCategoria() {

		return ProdutoVO.builder()
				.nome("Limão")
				.descricao("Kg de limão")
				.preco(BigDecimal.ONE)
				.quantidade(4L)
				.categoria(CategoriaVO.builder().id(3333L).build())
				.fornecedor(FornecedorProdutoVO.builder().id(1L).build())
				.build();
	}

	private ProdutoAlteraVO gerarAlterarProdutoVO() {

		return ProdutoAlteraVO.builder()
				.descricao("Kg de LIMÃO")
				.preco(BigDecimal.valueOf(6))
				.quantidade(1L)
				.build();
	}
	
	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

	@BeforeEach
	void inicializar() {
		path.append(PRODUTOS);
	}

	@AfterEach
	void finalizar() {
		path = new StringBuilder(URL);
	}
}
