package com.marcelo.gestao.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.marcelo.gestao.domain.dto.FornecedorDTO;
import com.marcelo.gestao.domain.dto.FornecedorResumidoDTO;
import com.marcelo.gestao.domain.vo.FiltroVO;
import com.marcelo.gestao.domain.vo.FornecedorVO;
import com.marcelo.gestao.service.FornecedorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/fornecedores")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class FornecedorController {

	private final FornecedorService fornecedorService;

	@GetMapping("/filtros")
	public Page<FornecedorResumidoDTO> buscarFornecedorPorFiltros(@PageableDefault(size = 20) Pageable pegeable,
			FiltroVO filtros) {

		return fornecedorService.buscarFornecedorPorFiltros(filtros, pegeable);
	}

	@GetMapping("/{idFornecedor}")
	public FornecedorDTO buscarFornecedorPorId(@PathVariable Long idFornecedor) {

		return fornecedorService.buscarFornecedorPorId(idFornecedor);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FornecedorDTO cadastrarFornecedor(@RequestBody @Valid FornecedorVO fornecedor) {

		return fornecedorService.cadastrarFornecedor(fornecedor);
	}

	@PutMapping("/{idFornecedor}")
	public FornecedorDTO alterarFornecedor(@PathVariable Long idFornecedor,
			@RequestBody @Valid FornecedorVO fornecedor) {

		return fornecedorService.alterarFornecedor(idFornecedor, fornecedor);
	}
	
	@DeleteMapping("/{idFornecedor}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteFornecedor(@PathVariable Long idFornecedor) {

	 fornecedorService.deleteFornecedor(idFornecedor);
	}

}
