package com.marcelo.gestao.domain.dto;

import com.marcelo.gestao.domain.dto.nested.CategoriaDTO;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProdutoResumidoDTO {

	private Long id;
	
	private String nome;
	
	private CategoriaDTO categoria;
}
