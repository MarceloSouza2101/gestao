package com.marcelo.gestao.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcelo.gestao.domain.dto.nested.CategoriaDTO;
import com.marcelo.gestao.domain.dto.nested.PaisDTO;
import com.marcelo.gestao.service.DominioService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/dominios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class DominioController {

	private final DominioService dominioService;
	
	@GetMapping("/paises")
	@Operation( 
			summary = "Recupera os paises", 
			description = "Permite ao usuário consultar todos paises.")
	public List<PaisDTO> buscarPaises() {

		return dominioService.buscarPaises();
	}
	
	@GetMapping("/categorias")
	@Operation( 
			summary = "Recupera os categorias", 
			description = "Permite ao usuário consultar todos categorias.")
	public List<CategoriaDTO> buscarCategorias() {

		return dominioService.buscarCategorias();
	}
}
