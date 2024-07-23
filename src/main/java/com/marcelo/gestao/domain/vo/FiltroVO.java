package com.marcelo.gestao.domain.vo;

import lombok.Builder;
import lombok.Generated;
import lombok.Getter;

@Generated
@Builder
@Getter
public class FiltroVO {

	private String nomeFornecedor;

	private String codigoPais;

	private Long idProduto;

	public boolean incluirNomeFornecedor() {
		return this.nomeFornecedor != null;
	}

	public boolean incluirCodigoPais() {
		return this.codigoPais != null;
	}

	public boolean incluirIdProduto() {
		return this.idProduto != null;
	}

}
