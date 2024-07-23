package com.marcelo.gestao.domain.vo;

import lombok.Builder;
import lombok.Generated;
import lombok.Getter;

@Generated
@Builder
@Getter
public class FiltroProdutoVO {

	private String nomeProduto;

	private Long quantidade;
	
	private Long idCategoria;
	
	private Long idFornecedor;

	public boolean incluirNomeProduto() {
		return this.nomeProduto != null;
	}

	public boolean incluirIdCategoria() {
		return this.idCategoria != null;
	}

	public boolean incluirIdFornecedor() {
		return this.idFornecedor != null;
	}
	
	public boolean incluirQuantidade() {
		return this.quantidade != null;
	}
}
