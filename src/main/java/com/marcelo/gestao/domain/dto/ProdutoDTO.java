package com.marcelo.gestao.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.marcelo.gestao.domain.dto.nested.CategoriaDTO;
import com.marcelo.gestao.domain.dto.nested.PaisDTO;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProdutoDTO {

	private Long id;

	private String nome;

	private PaisDTO pais;

	private String descricao;

	private BigDecimal preco;

	private Long quantidade;

	private LocalDateTime dataCriacao;

	private FornecedorResumidoDTO fornecedor;

	private CategoriaDTO categoria;
	
}
