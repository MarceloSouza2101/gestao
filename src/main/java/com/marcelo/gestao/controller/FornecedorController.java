package com.marcelo.gestao.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/fornecedores")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class FornecedorController {

	private final FornecedorService fornecedorService;

	@GetMapping(path = "/filtros", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation( 
			summary = "Recupera os fornecedores por filtro fornecido.", 
			description = "Permite ao usuário consultar os fornecedores disponiveis.") 
	public Page<FornecedorResumidoDTO> buscarFornecedorPorFiltros(@PageableDefault(size = 20) Pageable pegeable,
			FiltroVO filtros) {

		return fornecedorService.buscarFornecedorPorFiltros(filtros, pegeable);
	}

	@GetMapping(path = "/{idFornecedor}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation( 
			summary = "Recupera os fornecedores por id.", 
			description = "Permite ao usuário consultar um fornecedor de forma mais detalhada.") 
	public FornecedorDTO buscarFornecedorPorId(@PathVariable Long idFornecedor) {

		return fornecedorService.buscarFornecedorPorId(idFornecedor);
	}

	@PostMapping
	@Operation( 
			summary = "Cadastrar um novo fornecedor.", 
			description = "Permite ao usuário cadastrar um novo fornecedor.")
	@ResponseStatus(HttpStatus.CREATED)
	public FornecedorDTO cadastrarFornecedor(@RequestBody @Valid FornecedorVO fornecedor) {

		return fornecedorService.cadastrarFornecedor(fornecedor);
	}

	@PutMapping(path = "/{idFornecedor}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation( 
			summary = "Alterar um fornecedor por id.", 
			description = "Permite ao usuário alterar um fornecedor.")
	public FornecedorDTO alterarFornecedor(@PathVariable Long idFornecedor,
			@RequestBody @Valid FornecedorVO fornecedor) {

		return fornecedorService.alterarFornecedor(idFornecedor, fornecedor);
	}
	
	@DeleteMapping("/{idFornecedor}")
	@Operation( 
			summary = "Deletar um fornecedor por id.", 
			description = "Permite ao usuário deletar um fornecedor.")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteFornecedor(@PathVariable Long idFornecedor) {

	 fornecedorService.deleteFornecedor(idFornecedor);
	}

}
