package com.marcelo.gestao.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.marcelo.gestao.domain.entity.ProdutoEntity;
import com.marcelo.gestao.domain.vo.FiltroProdutoVO;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class ProdutoSpecification implements Specification<ProdutoEntity> {

	private static final long serialVersionUID = 1L;

	private transient FiltroProdutoVO filtros;

	public ProdutoSpecification(FiltroProdutoVO filtros) {
		this.filtros = filtros;
	}

	@Override
	public Predicate toPredicate(Root<ProdutoEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

		List<Predicate> condicoes = new ArrayList<>();

		if (filtros.incluirNomeProduto()) {
			Predicate nome = criteriaBuilder.like(root.get("nome"), filtros.getNomeProduto() + "%");
			condicoes.add(nome);
		}

		if (filtros.incluirQuantidade()) {
			Predicate nome = criteriaBuilder.equal(root.get("quantidade"), filtros.getQuantidade());
			condicoes.add(nome);
		}
		
		if (filtros.incluirIdCategoria()) {
			Predicate nome = criteriaBuilder.equal(root.get("categoria").get("id"), filtros.getIdCategoria());
			condicoes.add(nome);
		}
		
		if (filtros.incluirIdFornecedor()) {
			Predicate nome = criteriaBuilder.equal(root.get("fornecedor").get("id"), filtros.getIdFornecedor());
			condicoes.add(nome);
		}

		return criteriaBuilder.and(condicoes.toArray(new Predicate[0]));
	}

}
