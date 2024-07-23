package com.marcelo.gestao.domain.entity.factory;

import com.marcelo.gestao.domain.entity.FornecedorEntity;
import com.marcelo.gestao.domain.vo.FornecedorVO;

import lombok.Generated;

@Generated
public class FornecedorEntityFactory {

	FornecedorEntityFactory() {
	}

	public static FornecedorEntity converterParaEntity(FornecedorVO fornecedor) {

		return FornecedorEntity.builder().nome(fornecedor.getNome()).codigoPais(fornecedor.getCodigoPais()).build();
	}

	public static FornecedorEntity converterParaAlterarEntity(FornecedorVO fornecedor, FornecedorEntity entity) {

		entity.setCodigoPais(fornecedor.getCodigoPais());
		entity.setNome(fornecedor.getNome());

		return entity;
	}

}
