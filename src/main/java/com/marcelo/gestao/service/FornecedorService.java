package com.marcelo.gestao.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.marcelo.gestao.domain.dto.FornecedorDTO;
import com.marcelo.gestao.domain.dto.FornecedorResumidoDTO;
import com.marcelo.gestao.domain.dto.factory.FornecedorDTOFactory;
import com.marcelo.gestao.domain.dto.factory.FornecedorResumidoDTOFactory;
import com.marcelo.gestao.domain.dto.nested.PaisFonteDTO;
import com.marcelo.gestao.domain.entity.FornecedorEntity;
import com.marcelo.gestao.domain.entity.factory.FornecedorEntityFactory;
import com.marcelo.gestao.domain.vo.FiltroVO;
import com.marcelo.gestao.domain.vo.FornecedorVO;
import com.marcelo.gestao.exception.DadosJaCadastradosException;
import com.marcelo.gestao.exception.NaoEncontradoException;
import com.marcelo.gestao.repository.FornecedorRepository;
import com.marcelo.gestao.service.client.GenericClient;
import com.marcelo.gestao.specification.FornecedorSpecification;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FornecedorService {

	private final GenericClient client;

	private final FornecedorRepository fornecedorRepository;

	public Page<FornecedorResumidoDTO> buscarFornecedorPorFiltros(FiltroVO filtros, Pageable pageable) {

		Page<FornecedorEntity> page = fornecedorRepository.findAll(new FornecedorSpecification(filtros), pageable);

		return FornecedorResumidoDTOFactory.converterPageParaDTO(page);
	}

	public FornecedorDTO buscarFornecedorPorId(Long idFornecedor) {

		FornecedorEntity fornecedorEntity = recuperarFornecedor(idFornecedor);

		List<PaisFonteDTO> paises = extrairPais(fornecedorEntity.getCodigoPais());

		return FornecedorDTOFactory.converterParaDTO(fornecedorEntity, paises);
	}

	@Transactional
	public FornecedorDTO cadastrarFornecedor(FornecedorVO fornecedor) {

		FornecedorEntity fornecedorEntity = extrairFornecedor(fornecedor);

		return FornecedorDTO.builder().id(fornecedorEntity.getId()).build();
	}

	@Transactional
	public FornecedorDTO alterarFornecedor(Long idFornecedor, @Valid FornecedorVO fornecedor) {

		extrairFornecedorAlteracao(recuperarFornecedor(idFornecedor), fornecedor);

		return FornecedorDTO.builder().id(idFornecedor).build();
	}

	@Transactional
	public void deleteFornecedor(Long idFornecedor) {

		FornecedorEntity entity = recuperarFornecedor(idFornecedor);
		
		fornecedorRepository.delete(entity);
	}
	
	private void extrairFornecedorAlteracao(FornecedorEntity entity, FornecedorVO fornecedor) {

		FornecedorEntity entityAlterado = FornecedorEntityFactory.converterParaAlterarEntity(fornecedor, entity);

		if (fornecedorRepository.existsByNomeAndCodigoPaisAndIdNot(entityAlterado.getNome(),
				entityAlterado.getCodigoPais(), entityAlterado.getId())) {
			throw new DadosJaCadastradosException("Já existe um fornecedor com esse nome nesse país");
		}

		validarPais(entityAlterado.getCodigoPais());

	}

	private FornecedorEntity extrairFornecedor(FornecedorVO fornecedor) {

		FornecedorEntity entity = FornecedorEntityFactory.converterParaEntity(fornecedor);

		if (fornecedorRepository.existsByNomeAndCodigoPais(entity.getNome(), entity.getCodigoPais())) {
			throw new DadosJaCadastradosException("Já existe um fornecedor com esse nome nesse país");
		}

		validarPais(entity.getCodigoPais());

		return fornecedorRepository.save(entity);
	}
	
	private List<PaisFonteDTO> extrairPais(String codigoPais) {

		List<PaisFonteDTO> retorno = client.getLista("/alpha?codes=" + codigoPais + "&fields=ccn3,translations");

		return retorno;
	}

	private void validarPais(String codigoPais) {

		if (client.getValidarCodigo("/alpha?codes=" + codigoPais + "&fields=cca3", String.class) == null) {
			throw new NaoEncontradoException("Nenhum país foi encontrado com esse codigo: " + codigoPais);
		}
	}

	private FornecedorEntity recuperarFornecedor(Long idFornecedor) {

		return fornecedorRepository.findById(idFornecedor)
				.orElseThrow(() -> new NaoEncontradoException("Nenhum fornecedor encontrado"));
	}
}
