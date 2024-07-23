package com.marcelo.gestao.domain.dto.factory;

import org.springframework.data.domain.Page;

import com.marcelo.gestao.domain.dto.FornecedorResumidoDTO;
import com.marcelo.gestao.domain.entity.FornecedorEntity;

import lombok.Generated;

@Generated
public class FornecedorResumidoDTOFactory {

	FornecedorResumidoDTOFactory() {
	}

	public static FornecedorResumidoDTO converterParaDTO(FornecedorEntity page) {

		return FornecedorResumidoDTO.builder()
				.id(page.getId())
				.nome(page.getNome())
				.build();
	}

	public static Page<FornecedorResumidoDTO> converterPageParaDTO(Page<FornecedorEntity> page) {

		return page.map(FornecedorResumidoDTOFactory::converterParaDTO);
	}
}
