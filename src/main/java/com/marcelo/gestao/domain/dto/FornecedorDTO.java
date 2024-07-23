package com.marcelo.gestao.domain.dto;

import java.util.List;

import com.marcelo.gestao.domain.dto.nested.PaisDTO;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FornecedorDTO {

	private Long id;

	private String nome;

	private PaisDTO pais;
	
	private List<ProdutoResumidoDTO> produtos;

}
