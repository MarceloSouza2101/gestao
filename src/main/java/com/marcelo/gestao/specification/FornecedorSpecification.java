package com.marcelo.gestao.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.marcelo.gestao.domain.entity.FornecedorEntity;
import com.marcelo.gestao.domain.vo.FiltroVO;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class FornecedorSpecification implements Specification<FornecedorEntity> {

	private static final long serialVersionUID = 1L;

	private transient FiltroVO filtros;

	public FornecedorSpecification(FiltroVO filtros) {
		this.filtros = filtros;
	}

	@Override
	public Predicate toPredicate(Root<FornecedorEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

		List<Predicate> condicoes = new ArrayList<>();

		if (filtros.incluirNomeFornecedor()) {
			Predicate nome = criteriaBuilder.like(root.get("nome"), filtros.getNomeFornecedor() + "%");
			condicoes.add(nome);
		}

		if (filtros.incluirCodigoPais()) {
			Predicate nome = criteriaBuilder.equal(root.get("codigoPais"), filtros.getCodigoPais());
			condicoes.add(nome);
		}
		
		if (filtros.incluirIdProduto()) {
			Predicate nome = criteriaBuilder.equal(root.join("produtos").get("id"), filtros.getIdProduto());
			condicoes.add(nome);
		}

		return criteriaBuilder.and(condicoes.toArray(new Predicate[0]));
	}

}
