package com.marcelo.gestao.domain.dto.factory;

import java.util.List;

import org.springframework.data.domain.Page;

import com.marcelo.gestao.domain.dto.ProdutoResumidoDTO;
import com.marcelo.gestao.domain.entity.ProdutoEntity;

import lombok.Generated;

@Generated
public class ProdutoResumidoDTOFactory {

	ProdutoResumidoDTOFactory(){}

	public static ProdutoResumidoDTO converterParaDTO(ProdutoEntity produto) {
		
		return ProdutoResumidoDTO.builder()
				.id(produto.getId())
				.nome(produto.getNome())
				.categoria(CategoriaDTOFactory.converterParaDTO(produto.getCategoria()))
				.build();
	}
	
	public static List<ProdutoResumidoDTO> converterParaListaDTO(List<ProdutoEntity> produtos) {

		return produtos.stream().map(ProdutoResumidoDTOFactory::converterParaDTO).toList();
	}

	public static Page<ProdutoResumidoDTO> converterPageParaDTO(Page<ProdutoEntity> page) {

		return page.map(ProdutoResumidoDTOFactory::converterParaDTO);
	}
}
