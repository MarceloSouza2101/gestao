package com.marcelo.gestao.domain.dto.factory;

import java.util.List;

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

	public static List<CategoriaDTO> converterParaLista(List<CategoriaEntity> listaEntity) {

		return listaEntity.stream().map(CategoriaDTOFactory::converterParaDTO).toList();
	}
}
