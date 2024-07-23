package com.marcelo.gestao.domain.entity.factory;

import com.marcelo.gestao.domain.entity.ProdutoEntity;
import com.marcelo.gestao.domain.vo.ProdutoAlteraVO;
import com.marcelo.gestao.domain.vo.ProdutoVO;

import lombok.Generated;

@Generated
public class ProdutoEntityFactory {

	ProdutoEntityFactory() {
	}

	public static ProdutoEntity converterParaEntity(ProdutoVO produtoVO) {

		return ProdutoEntity.builder().nome(produtoVO.getNome()).descricao(produtoVO.getDescricao())
				.preco(produtoVO.getPreco()).quantidade(produtoVO.getQuantidade()).build();
	}

	public static ProdutoEntity converterParaAlterarEntity(ProdutoEntity produtoEntity, ProdutoAlteraVO produtoVO) {

		produtoEntity.setQuantidade(produtoVO.getQuantidade());
		produtoEntity.setDescricao(produtoVO.getDescricao());
		produtoEntity.setPreco(produtoVO.getPreco());
		
		return produtoEntity;
	}

}
