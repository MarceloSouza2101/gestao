package com.marcelo.gestao.domain.dto.factory;

import com.marcelo.gestao.domain.dto.nested.CategoriaDTO;
import com.marcelo.gestao.domain.entity.CategoriaEntity;

import lombok.Generated;

@Generated
public class CategoriaDTOFactory {

	CategoriaDTOFactory(){}

	public static CategoriaDTO converterParaDTO(CategoriaEntity categoriaEntity) {

		return CategoriaDTO.builder()
				.id(categoriaEntity.getId())
				.descricao(categoriaEntity.getDescricao())
				.build();
	}
}
