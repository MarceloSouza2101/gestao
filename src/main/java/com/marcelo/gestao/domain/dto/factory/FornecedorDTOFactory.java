package com.marcelo.gestao.domain.dto.factory;

import java.util.List;

import com.marcelo.gestao.domain.dto.FornecedorDTO;
import com.marcelo.gestao.domain.dto.nested.PaisFonteDTO;
import com.marcelo.gestao.domain.entity.FornecedorEntity;

import lombok.Generated;

@Generated
public class FornecedorDTOFactory {

	FornecedorDTOFactory() {
	}

	public static FornecedorDTO converterParaDTO(FornecedorEntity fornecedorEntity, List<PaisFonteDTO> paises) {

		return FornecedorDTO.builder()
				.id(fornecedorEntity.getId())
				.nome(fornecedorEntity.getNome())
				.pais(PaisDTOFactory.converterParaDTO(paises))
				.produtos(ProdutoResumidoDTOFactory.converterParaListaDTO(fornecedorEntity.getProdutos()))
				.build();
	}
}
