package com.marcelo.gestao.domain.dto.factory;

import com.marcelo.gestao.domain.dto.ProdutoDTO;
import com.marcelo.gestao.domain.entity.ProdutoEntity;

import lombok.Generated;

@Generated
public class ProdutoDTOFactory {

	ProdutoDTOFactory(){}

	public static ProdutoDTO converterParaDTO(ProdutoEntity produtoEntity) {

		return ProdutoDTO.builder()
				.id(produtoEntity.getId())
				.nome(produtoEntity.getNome())
				.descricao(produtoEntity.getDescricao())
				.preco(produtoEntity.getPreco())
				.quantidade(produtoEntity.getQuantidade())
				.dataCriacao(produtoEntity.getDataCriacao())
				.fornecedor(FornecedorResumidoDTOFactory.converterParaDTO(produtoEntity.getFornecedor()))
				.categoria(CategoriaDTOFactory.converterParaDTO(produtoEntity.getCategoria()))
				.build();
	}
	
	
}
