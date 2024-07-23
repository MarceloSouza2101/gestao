package com.marcelo.gestao.domain.dto.nested;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CategoriaDTO {

	private Long id;

	private String descricao;
}
