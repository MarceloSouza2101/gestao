package com.marcelo.gestao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marcelo.gestao.domain.entity.FornecedorEntity;

@Repository
public interface FornecedorRepository extends JpaRepository<FornecedorEntity, Long> {

	boolean existsByNomeAndCodigoPaisAndIdNot(String nome, String codigoPais, Long id);

	boolean existsByNomeAndCodigoPais(String nome, String codigoPais);

	Page<FornecedorEntity> findAll(Specification<FornecedorEntity> specs, Pageable pageable);

}
