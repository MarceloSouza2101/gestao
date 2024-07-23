package com.marcelo.gestao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marcelo.gestao.domain.entity.ProdutoEntity;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {

	boolean existsByFornecedorIdAndCategoriaIdAndNome(Long idFornecedor, Long idCategoria, String nome);

	Page<ProdutoEntity> findAll(Specification<ProdutoEntity> specs, Pageable pegeable);

}
