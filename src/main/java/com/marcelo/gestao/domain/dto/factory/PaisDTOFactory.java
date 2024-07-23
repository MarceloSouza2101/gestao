package com.marcelo.gestao.domain.dto.factory;

import java.util.List;
import java.util.Optional;

import com.marcelo.gestao.domain.dto.nested.PaisDTO;
import com.marcelo.gestao.domain.dto.nested.PaisFonteDTO;

import lombok.Generated;

@Generated
public class PaisDTOFactory {

	PaisDTOFactory() {
	}

	public static PaisDTO converterParaDTO(List<PaisFonteDTO> paises) {

		Optional<PaisFonteDTO> pais = paises.stream().findFirst();

		if (pais.isPresent()) {
			return PaisDTO.builder().codigo(pais.get().getCcn3())
					.nome(pais.get().getTranslations().getPor().getCommon()).build();
		}

		return null;
	}

}
