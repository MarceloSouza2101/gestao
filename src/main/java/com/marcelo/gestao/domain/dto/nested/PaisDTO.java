package com.marcelo.gestao.domain.dto.nested;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PaisDTO {

	private String codigo;

	private String nome;
}
