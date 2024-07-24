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

import com.marcelo.gestao.domain.dto.ProdutoDTO;
import com.marcelo.gestao.domain.dto.ProdutoResumidoDTO;
import com.marcelo.gestao.domain.vo.FiltroProdutoVO;
import com.marcelo.gestao.domain.vo.ProdutoAlteraVO;
import com.marcelo.gestao.domain.vo.ProdutoVO;
import com.marcelo.gestao.service.ProdutoService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class ProdutoController {

	private final ProdutoService produtoService;

	@GetMapping("/filtros")
	@Operation( //
			summary = "Recupera os produtos por filtro fornecido.", //
			description = "Permite ao usuário consultar os produtos disponiveis.")
	public Page<ProdutoResumidoDTO> buscarProdutoPorFiltros(@PageableDefault(size = 20) Pageable pegeable,
			FiltroProdutoVO filtros) {

		return produtoService.buscarProdutoPorFiltros(filtros, pegeable);
	}
	
	@GetMapping("/{idProduto}")
	@Operation( 
			summary = "Recupera os produtos por id.", 
			description = "Permite ao usuário consultar um produto de forma mais detalhada.")
	public ProdutoDTO buscarProdutoPorId(@PathVariable Long idProduto) {

		return produtoService.buscarProdutoPorId(idProduto);
	}
	
	@PostMapping
	@Operation( 
			summary = "Cadastrar um novo produto.", 
			description = "Permite ao usuário cadastrar um novo produto.")
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoDTO cadastrarProduto(@RequestBody @Valid ProdutoVO produto) {

		return produtoService.cadastrarProduto(produto);
	}
	
	@PutMapping(path = "/{idProduto}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation( 
			summary = "Alterar um produto por id.", 
			description = "Permite ao usuário alterar um produto.")
	public ProdutoDTO alterarProduto(@PathVariable Long idProduto,@RequestBody @Valid ProdutoAlteraVO produto) {

		return produtoService.alterarProduto(idProduto, produto);
	}

	@DeleteMapping("/{idProduto}")
	@Operation( 
			summary = "Deletar um produto por id.", 
			description = "Permite ao usuário deletar um produto.")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteProduto(@PathVariable Long idProduto) {

		produtoService.deleteProduto(idProduto);
	}
}
