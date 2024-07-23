package com.marcelo.gestao.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.marcelo.gestao.domain.dto.ProdutoDTO;
import com.marcelo.gestao.domain.dto.ProdutoResumidoDTO;
import com.marcelo.gestao.domain.dto.factory.ProdutoDTOFactory;
import com.marcelo.gestao.domain.dto.factory.ProdutoResumidoDTOFactory;
import com.marcelo.gestao.domain.entity.CategoriaEntity;
import com.marcelo.gestao.domain.entity.FornecedorEntity;
import com.marcelo.gestao.domain.entity.ProdutoEntity;
import com.marcelo.gestao.domain.entity.factory.ProdutoEntityFactory;
import com.marcelo.gestao.domain.vo.FiltroProdutoVO;
import com.marcelo.gestao.domain.vo.ProdutoAlteraVO;
import com.marcelo.gestao.domain.vo.ProdutoVO;
import com.marcelo.gestao.exception.DadosJaCadastradosException;
import com.marcelo.gestao.exception.NaoEncontradoException;
import com.marcelo.gestao.repository.CategoriaRepository;
import com.marcelo.gestao.repository.FornecedorRepository;
import com.marcelo.gestao.repository.ProdutoRepository;
import com.marcelo.gestao.specification.ProdutoSpecification;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdutoService {

	private final FornecedorRepository fornecedorRepository;

	private final ProdutoRepository produtoRepository;

	private final CategoriaRepository categoriaRepository;

	public Page<ProdutoResumidoDTO> buscarProdutoPorFiltros(FiltroProdutoVO filtros, Pageable pegeable) {

		Page<ProdutoEntity> page = produtoRepository.findAll(new ProdutoSpecification(filtros), pegeable);
		
		return ProdutoResumidoDTOFactory.converterPageParaDTO(page);
	}
	
	public ProdutoDTO buscarProdutoPorId(Long idProduto) {

		ProdutoEntity produtoEntity = recuperarProduto(idProduto);
		
		return ProdutoDTOFactory.converterParaDTO(produtoEntity);
	}

	@Transactional
	public ProdutoDTO cadastrarProduto(ProdutoVO produtoVO) {

		ProdutoEntity produtoEntity = extrairProduto(produtoVO);

		return ProdutoDTO.builder().id(produtoEntity.getId()).build();
	}
	
	@Transactional
	public ProdutoDTO alterarProduto(Long idProduto, ProdutoAlteraVO produtoVO) {

		extrairProdutoAlteracao(recuperarProduto(idProduto), produtoVO);

		return ProdutoDTO.builder().id(idProduto).build();
	}
	
	@Transactional
	public void deleteProduto(Long idProduto) {

		ProdutoEntity entity = recuperarProduto(idProduto);
		
		produtoRepository.delete(entity);
	}

	private void extrairProdutoAlteracao(ProdutoEntity produtoEntity, ProdutoAlteraVO produtoVO) {

		ProdutoEntity entity = ProdutoEntityFactory.converterParaAlterarEntity(produtoEntity, produtoVO);
		
		produtoRepository.save(entity);
	}

	private ProdutoEntity extrairProduto(ProdutoVO produtoVO) {

		ProdutoEntity produtoEntity = ProdutoEntityFactory.converterParaEntity(produtoVO);

		produtoEntity.setFornecedor(recuperarFornecedor(produtoVO.getFornecedor().getId()));
		produtoEntity.setCategoria(recuperarCategoria(produtoVO.getCategoria().getId()));

		if(produtoRepository.existsByFornecedorIdAndCategoriaIdAndNome(produtoEntity.getFornecedor().getId(), produtoEntity.getCategoria().getId(),
				produtoEntity.getNome())) {
			throw new DadosJaCadastradosException("Já existe esse produto para esse fornecedor");
		}
		
		return produtoRepository.save(produtoEntity);
	}

	private FornecedorEntity recuperarFornecedor(Long idFornecedor) {

		return fornecedorRepository.findById(idFornecedor)
				.orElseThrow(() -> new NaoEncontradoException("Nenhum fornecedor encontrado"));
	}

	private ProdutoEntity recuperarProduto(Long idProduto) {

		return produtoRepository.findById(idProduto)
				.orElseThrow(() -> new NaoEncontradoException("Nenhum produto encontrado"));
	}
	
	private CategoriaEntity recuperarCategoria(Long idCategoria) {

		return categoriaRepository.findById(idCategoria)
				.orElseThrow(() -> new NaoEncontradoException("Nenhuma categoria foi encontrada"));
	}
}
