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

import com.marcelo.gestao.domain.dto.ProdutoDTO;
import com.marcelo.gestao.domain.dto.ProdutoResumidoDTO;
import com.marcelo.gestao.domain.vo.FiltroProdutoVO;
import com.marcelo.gestao.domain.vo.ProdutoAlteraVO;
import com.marcelo.gestao.domain.vo.ProdutoVO;
import com.marcelo.gestao.service.ProdutoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class ProdutoController {

	private final ProdutoService produtoService;

	@GetMapping("/filtros")
	public Page<ProdutoResumidoDTO> buscarProdutoPorFiltros(@PageableDefault(size = 20) Pageable pegeable,
			FiltroProdutoVO filtros) {

		return produtoService.buscarProdutoPorFiltros(filtros, pegeable);
	}
	
	@GetMapping("/{idProduto}")
	public ProdutoDTO buscarProdutoPorId(@PathVariable Long idProduto) {

		return produtoService.buscarProdutoPorId(idProduto);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoDTO cadastrarProduto(@RequestBody @Valid ProdutoVO produto) {

		return produtoService.cadastrarProduto(produto);
	}
	
	@PutMapping("/{idProduto}")
	public ProdutoDTO alterarProduto(@PathVariable Long idProduto,@RequestBody @Valid ProdutoAlteraVO produto) {

		return produtoService.alterarProduto(idProduto, produto);
	}

	@DeleteMapping("/{idProduto}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteProduto(@PathVariable Long idProduto) {

		produtoService.deleteProduto(idProduto);
	}
}
