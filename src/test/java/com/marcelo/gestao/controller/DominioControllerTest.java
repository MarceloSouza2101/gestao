package com.marcelo.gestao.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

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

import com.marcelo.gestao.domain.dto.nested.CategoriaDTO;
import com.marcelo.gestao.domain.dto.nested.PaisDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
class DominioControllerTest extends AbstractController{
	
	@Autowired
	protected TestRestTemplate restTemplate;

	private static final String DOMINIO = "/gestao/dominios";
	
	private static StringBuilder path = new StringBuilder(URL);
	
	@Test
	@Order(1)
	void buscarCategorias() {
		
		log.info("#INICIO-METRICAS dominio-controller.buscarCategorias");
						
		HttpHeaders headers = getHeaders();
		HttpEntity<Void> entity = new HttpEntity<Void>(headers);		
		
		ParameterizedTypeReference<List<CategoriaDTO>> responseType = new ParameterizedTypeReference<>() {};
		
		path.append("/").append("categorias");

		ResponseEntity<List<CategoriaDTO>> response = restTemplate.exchange(path.toString(), HttpMethod.GET, entity, responseType);

		assertTrue(response.getBody().size() > 0L);
		assertEquals(HttpStatus.OK, response.getStatusCode());
				
		log.info("#FIM-METRICAS");

	}
	
	@Test
	@Order(2)
	void buscarPais() {
		
		log.info("#INICIO-METRICAS dominio-controller.buscarCategorias");
				
		HttpHeaders headers = getHeaders();
		HttpEntity<Void> entity = new HttpEntity<Void>(headers);		
		
		ParameterizedTypeReference<List<PaisDTO>> responseType = new ParameterizedTypeReference<>() {};
		
		path.append("/").append("paises");

		ResponseEntity<List<PaisDTO>> response = restTemplate.exchange(path.toString(), HttpMethod.GET, entity, responseType);

		assertTrue(response.getBody().size() > 0L);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		log.info("#FIM-METRICAS");

	}
	
	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

	@BeforeEach
	void inicializar() {
		path.append(DOMINIO);
	}

	@AfterEach
	void finalizar() {
		path = new StringBuilder(URL);
	}
}
